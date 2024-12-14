package com.nevidimka655.astracrypt.data.repository.files

import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.repository.files.FilesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class FilesRepositoryProvider(
    private val plainFilesRepository: FilesRepository,
    aeadInfoFlow: Flow<AeadInfo>
) {

    val filesRepository: Flow<FilesRepository> = aeadInfoFlow.map {
        if (it.database != null) plainFilesRepository else plainFilesRepository
    }.distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> repositoryFlow(selector: (FilesRepository) -> Flow<T>): Flow<T> {
        return filesRepository.flatMapLatest { selector(it) }
    }

}