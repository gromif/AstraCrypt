package io.gromif.astracrypt.files.data.provider

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.provider.PagingProvider
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class PagingProviderImpl(
    private val filesDao: FilesDao,
    private val pagingConfig: PagingConfig,
    private val aeadHandler: AeadHandler,
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) : PagingProvider<PagingData<Item>> {
    private var pagingSource: PagingSource<Int, PagerTuple>? = null
    private val searchQueryState = MutableStateFlow<String?>(null)
    private val searchFolderIdState = MutableStateFlow<List<Long>>(emptyList())
    private var sortingSecondType = MutableStateFlow(1)

    override fun provide(
        parentIdState: StateFlow<Long>,
        isStarredMode: Boolean,
    ): Flow<PagingData<Item>> {
        val aeadInfoFlow = settingsRepository.getAeadInfoFlow().onEach {
            sortingSecondType.emit(if (it.name) 6 else 1)
            invalidate()
        }
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                with(filesDao) {
                    if (isStarredMode) listStarred(
                        query = searchQueryState.value,
                        sortingItemType = ItemType.Folder.ordinal,
                        sortingSecondType = sortingSecondType.value
                    ) else listDefault(
                        rootId = parentIdState.value,
                        query = searchQueryState.value,
                        rootIdsToSearch = searchFolderIdState.value,
                        sortingItemType = ItemType.Folder.ordinal,
                        sortingSecondType = sortingSecondType.value
                    )
                }.also { pagingSource = it }
            }
        ).flow.combine(aeadInfoFlow) { pd, aeadInfo ->
            pd.map { pagerTuple ->
                val databaseMode = aeadInfo.databaseMode
                val data = if (databaseMode is AeadMode.Template) {
                    aeadHandler.decryptPagerTuple(aeadInfo, databaseMode, pagerTuple)
                } else pagerTuple
                Item(
                    id = data.id,
                    name = data.name,
                    type = data.type,
                    preview = data.preview?.let {
                        FileSource(path = it, aeadIndex = data.previewAead)
                    },
                    state = ItemState.entries[data.state]
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