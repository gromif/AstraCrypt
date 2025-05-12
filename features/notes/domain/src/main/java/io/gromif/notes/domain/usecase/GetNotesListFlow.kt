package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.paging.PagingProvider
import kotlinx.coroutines.flow.Flow

class GetNotesListFlow<out T>(
    private val pagingProvider: PagingProvider<T>
) {

    operator fun invoke(): Flow<T> {
        return pagingProvider()
    }

}