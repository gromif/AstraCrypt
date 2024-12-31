package com.nevidimka655.astracrypt.data.paging

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.data.database.FileTypes
import com.nevidimka655.astracrypt.data.files.db.tuples.PagerTuple
import com.nevidimka655.astracrypt.data.repository.RepositoryProviderImpl
import com.nevidimka655.astracrypt.domain.model.db.FileItem
import com.nevidimka655.astracrypt.view.models.NavigatorDirectory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class FilesPagingProvider @Inject constructor(
    private val repositoryProviderImpl: RepositoryProviderImpl
) {
    private val pagingSource = MutableStateFlow<PagingSource<Int, PagerTuple>?>(null)

    fun invalidate() { pagingSource.value?.invalidate() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun createPagingSource(
        filesNavigatorList: SnapshotStateList<NavigatorDirectory>,
        searchQuery: MutableState<String?>,
        searchDirsIndexesList: SnapshotStateList<Long>
    ) = repositoryProviderImpl.repository.flatMapLatest { currentRepository ->
        Pager(
            PagingConfig(
                pageSize = AppConfig.PAGING_PAGE_SIZE,
                enablePlaceholders = AppConfig.PAGING_ENABLE_PLACEHOLDERS,
                initialLoadSize = AppConfig.PAGING_INITIAL_LOAD
            ),
            pagingSourceFactory = {
                currentRepository.getList(
                    parentDirectoryId = filesNavigatorList.lastOrNull()?.id ?: 0,
                    searchQuery = searchQuery.value,
                    dirIdsForSearch = searchDirsIndexesList.toImmutableList()
                ).also { pagingSource.value = it }
            }
        ).flow.map { pagingData ->
            pagingData.map {
                FileItem(
                    id = it.id,
                    name = it.name,
                    type = FileTypes.entries[it.itemType]
                )
            }
        }
    }

}