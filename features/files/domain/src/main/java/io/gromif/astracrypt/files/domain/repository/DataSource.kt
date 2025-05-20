package io.gromif.astracrypt.files.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {

    suspend fun getDataFlow(
        folderId: Long,
        searchRequest: String?
    ): Flow<T>

    fun setFolderId(id: Long)

    fun invalidate()
}