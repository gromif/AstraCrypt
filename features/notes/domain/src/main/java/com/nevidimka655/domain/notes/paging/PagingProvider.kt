package com.nevidimka655.domain.notes.paging

import androidx.paging.PagingData
import com.nevidimka655.domain.notes.model.Note
import kotlinx.coroutines.flow.Flow

interface PagingProvider {

    operator fun invoke(): Flow<PagingData<Note>>

}