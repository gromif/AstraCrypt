package com.nevidimka655.astracrypt.domain.repository.notes

import com.nevidimka655.notes.domain.repository.NotesRepository

abstract class NotesRepositoryDecorator(private val decoratedRepository: NotesRepository) :
    NotesRepository {



}