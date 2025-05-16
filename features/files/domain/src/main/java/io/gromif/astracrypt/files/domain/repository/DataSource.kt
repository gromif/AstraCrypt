package io.gromif.astracrypt.files.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DataSource<T> {

    fun provide(
        parentIdState: StateFlow<Long>,
        isStarredMode: Boolean = false
    ): Flow<T>

    suspend fun setSearchQuery(parentId: Long, query: String?)

    fun invalidate()
}