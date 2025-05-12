package io.gromif.notes.domain.paging

import kotlinx.coroutines.flow.Flow

interface PagingProvider<out T> {

    operator fun invoke(): Flow<T>

}