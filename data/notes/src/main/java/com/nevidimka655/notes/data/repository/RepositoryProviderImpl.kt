package com.nevidimka655.notes.data.repository

import com.nevidimka655.domain.notes.repository.Repository
import com.nevidimka655.domain.notes.repository.RepositoryProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RepositoryProviderImpl(
    private val repository: Repository,
    //aeadInfoFlow: Flow<AeadInfo> TODO(Core)
): RepositoryProvider {

    override val notesRepository: Flow<Repository> = MutableStateFlow(repository) /*aeadInfoFlow.map {
        if (it.database != null) notesRepositoryImpl else notesRepositoryImpl
    }.distinctUntilChanged()*/ // TODO(Core)

}