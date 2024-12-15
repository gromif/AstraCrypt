package com.nevidimka655.astracrypt.data.repository.notes

import com.nevidimka655.astracrypt.data.model.AeadInfo
import com.nevidimka655.astracrypt.domain.repository.notes.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class NotesRepositoryProvider(
    private val notesRepositoryImpl: NotesRepositoryImpl,
    aeadInfoFlow: Flow<AeadInfo>
) {

    val notesRepository: Flow<NotesRepository> = aeadInfoFlow.map {
        if (it.database != null) notesRepositoryImpl else notesRepositoryImpl
    }.distinctUntilChanged()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <T> repositoryFlow(selector: (NotesRepository) -> Flow<T>): Flow<T> {
        return notesRepository.flatMapLatest { selector(it) }
    }

}