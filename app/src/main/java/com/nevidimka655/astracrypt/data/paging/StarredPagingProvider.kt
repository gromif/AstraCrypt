package com.nevidimka655.astracrypt.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.map
import com.nevidimka655.astracrypt.app.AppConfig
import com.nevidimka655.astracrypt.data.database.FileTypes
import com.nevidimka655.astracrypt.data.files.db.tuples.PagerTuple
import com.nevidimka655.astracrypt.data.repository.RepositoryProviderImpl
import com.nevidimka655.astracrypt.domain.model.db.FileItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StarredPagingProvider @Inject constructor(
    private val repositoryProviderImpl: RepositoryProviderImpl
) {
    private val pagingSource = MutableStateFlow<PagingSource<Int, PagerTuple>?>(null)

    fun invalidate() { pagingSource.value?.invalidate() }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun createPagingSource(
        lastSearchQuery: String?
    ) = repositoryProviderImpl.repository.flatMapLatest { currentRepository ->
        Pager(
            PagingConfig(
                pageSize = AppConfig.PAGING_PAGE_SIZE,
                enablePlaceholders = AppConfig.PAGING_ENABLE_PLACEHOLDERS,
                initialLoadSize = AppConfig.PAGING_INITIAL_LOAD
            ),
            pagingSourceFactory = {
                currentRepository.getStarredList(lastSearchQuery).also { pagingSource.value = it }
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