package com.nevidimka655.domain.notes.usecase

import androidx.paging.PagingData
import com.nevidimka655.domain.notes.model.Note
import com.nevidimka655.domain.notes.paging.PagingProvider
import kotlinx.coroutines.flow.Flow

class GetPagingUseCase(
    private val pagingProvider: PagingProvider
) {

    operator fun invoke(): Flow<PagingData<Note>> {
        return pagingProvider()
    }

}