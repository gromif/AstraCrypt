package io.gromif.astracrypt.files.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataSource<T> {

    suspend fun provideFiles(searchRequest: String?): Flow<T>

    fun provideStarred(): Flow<T>

    suspend fun setSearchQuery(query: String?)

    fun setFolderId(id: Long)

    fun invalidate()
}