package com.nevidimka655.notes.data.paging

import androidx.paging.PagingData
import com.nevidimka655.domain.notes.model.Note
import com.nevidimka655.domain.notes.paging.PagingProvider
import com.nevidimka655.notes.data.repository.RepositoryProviderImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class PagingProviderImpl(
    private val repositoryProviderImpl: RepositoryProviderImpl
): PagingProvider {

    @OptIn(ExperimentalCoroutinesApi::class)
    override operator fun invoke(): Flow<PagingData<Note>> {
        return repositoryProviderImpl.notesRepository.flatMapLatest { repo ->
            repo.listOrderDescAsc()
        }
    }

}