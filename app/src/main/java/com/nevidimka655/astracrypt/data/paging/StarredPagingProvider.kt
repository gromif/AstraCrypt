package com.nevidimka655.astracrypt.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.data.repository.RepositoryProvider
import com.nevidimka655.astracrypt.domain.room.PagerTuple
import com.nevidimka655.astracrypt.app.config.AppConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class StarredPagingProvider @Inject constructor(
    private val repositoryProvider: RepositoryProvider
) {
    private val pagingSource = MutableStateFlow<PagingSource<Int, PagerTuple>?>(null)

    fun invalidate() { pagingSource.value?.invalidate() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun createPagingSource(
        lastSearchQuery: String?
    ) = repositoryProvider.repository.flatMapLatest { currentRepository ->
        Pager(
            PagingConfig(
                pageSize = AppConfig.PAGING_PAGE_SIZE,
                enablePlaceholders = AppConfig.PAGING_ENABLE_PLACEHOLDERS,
                initialLoadSize = AppConfig.PAGING_INITIAL_LOAD
            ),
            pagingSourceFactory = {
                currentRepository.getStarredList(lastSearchQuery).also { pagingSource.value = it }
            }
        ).flow
    }

}