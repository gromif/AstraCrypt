package io.gromif.astracrypt.files.data.provider

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.provider.PagingProvider
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.AeadUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PagingProviderImpl(
    private val filesDao: FilesDao,
    private val pagingConfig: PagingConfig,
    private val aeadUtil: AeadUtil,
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) : PagingProvider<PagingData<Item>> {
    private var pagingSource: PagingSource<Int, PagerTuple>? = null
    private val searchQueryState = MutableStateFlow<String?>(null)
    private val searchFolderIdState = MutableStateFlow<List<Long>>(emptyList())

    override fun provide(
        parentIdState: StateFlow<Long>,
        isStarredMode: Boolean,
    ): Flow<PagingData<Item>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                with(filesDao) {
                    if (isStarredMode) listStarred(
                        query = searchQueryState.value,
                        sortingItemType = FileType.Folder.ordinal,
                        sortingSecondType = 1
                    ) else listDefault(
                        rootId = parentIdState.value,
                        query = searchQueryState.value,
                        rootIdsToSearch = searchFolderIdState.value,
                        sortingItemType = FileType.Folder.ordinal,
                        sortingSecondType = 1
                    )
                }.also { pagingSource = it }
            }
        ).flow.map { pd ->
            val aeadInfo = settingsRepository.getAeadInfo()
            val isEncryptionEnabled = aeadInfo.databaseAeadIndex != -1

            suspend fun decrypt(data: String): String =
                aeadUtil.decrypt(aeadInfo.databaseAeadIndex, data)

            val pagingData = if (isEncryptionEnabled) pd.map {
                it.copy(
                    name = if (aeadInfo.name) decrypt(it.name) else it.name,
                    preview = it.preview?.let {
                        if (aeadInfo.preview) decrypt(it) else it
                    }
                )
            } else pd
            pagingData.map { pagerTuple ->
                Item(
                    id = pagerTuple.id,
                    name = pagerTuple.name,
                    type = pagerTuple.type,
                    preview = pagerTuple.preview?.let { 
                        FileSource(path = it, aeadIndex = pagerTuple.previewAead)
                    },
                    state = FileState.entries[pagerTuple.state]
                )
            }
        }
    }

    override suspend fun setSearchQuery(parentId: Long, query: String?) {
        val searchQuery = query?.takeIf { it.isNotEmpty() }
        if (searchQuery == searchQueryState.value) return
        searchQueryState.update { searchQuery }
        searchFolderIdState.update {
            if (searchQuery != null) repository.getFolderIds(parentId) else emptyList()
        }
        pagingSource?.invalidate()
    }

    override fun invalidate() {
        pagingSource?.invalidate()
    }

}