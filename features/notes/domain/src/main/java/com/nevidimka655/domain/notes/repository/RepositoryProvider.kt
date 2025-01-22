package com.nevidimka655.domain.notes.repository

import kotlinx.coroutines.flow.Flow

interface RepositoryProvider {

    val notesRepository: Flow<Repository>

}