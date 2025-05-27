package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import kotlinx.coroutines.flow.Flow

interface DataSource<T> {

    suspend fun getDataFlow(
        folderId: Long,
        searchRequest: String?,
        aeadInfo: AeadInfo
    ): Flow<T>
}
