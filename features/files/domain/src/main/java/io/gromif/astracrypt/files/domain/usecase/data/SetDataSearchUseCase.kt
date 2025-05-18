package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource

class SetDataSearchUseCase<T>(
    private val dataSource: DataSource<T>
) {

    suspend operator fun invoke(query: String?) {
        dataSource.setSearchQuery(query = query)
    }

}
