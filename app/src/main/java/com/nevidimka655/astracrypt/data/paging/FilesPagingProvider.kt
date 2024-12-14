package com.nevidimka655.astracrypt.data.paging

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.data.model.NavigatorDirectory
import com.nevidimka655.astracrypt.data.repository.files.FilesRepositoryProvider
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class FilesPagingProvider @Inject constructor(
    private val filesRepositoryProvider: FilesRepositoryProvider
) {
    private val pagingSource = MutableStateFlow<PagingSource<Int, PagerTuple>?>(null)

    fun invalidate() { pagingSource.value?.invalidate() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun createPagingSource(
        filesNavigatorList: SnapshotStateList<NavigatorDirectory>,
        searchQuery: MutableState<String?>,
        searchDirsIndexesList: SnapshotStateList<Long>
    ) = filesRepositoryProvider.filesRepository.flatMapLatest { currentRepository ->
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
        ).flow
    }

}