package com.nevidimka655.astracrypt.data.repository

import com.nevidimka655.astracrypt.domain.repository.Repository
import com.nevidimka655.astracrypt.data.model.AeadInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class RepositoryProvider(
    private val plainRepository: Repository,
    aeadInfoFlow: Flow<AeadInfo>
) {

    val repository: Flow<Repository> = aeadInfoFlow.map {
        if (it.database != null) plainRepository else plainRepository
    }.distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> repositoryFlow(selector: (Repository) -> Flow<T>): Flow<T> {
        return repository.flatMapLatest { selector(it) }
    }

}