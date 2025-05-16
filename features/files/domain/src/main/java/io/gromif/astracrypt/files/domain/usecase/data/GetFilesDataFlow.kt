package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetFilesDataFlow<T>(
    private val dataSource: DataSource<T>
) {

    operator fun invoke(parentIdState: StateFlow<Long>): Flow<T> {
        return dataSource.provide(parentIdState = parentIdState, isStarredMode = false)
    }

}
