package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource
import kotlinx.coroutines.flow.Flow

class GetFilesDataFlow<T>(
    private val dataSource: DataSource<T>
) {

    operator fun invoke(): Flow<T> {
        return dataSource.provide()
    }

}
