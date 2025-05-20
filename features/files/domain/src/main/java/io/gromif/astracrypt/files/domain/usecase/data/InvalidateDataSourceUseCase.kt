package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource

class InvalidateDataSourceUseCase<T>(
    private val dataSource: DataSource<T>
) {

    operator fun invoke() {
        dataSource.invalidate()
    }

}
