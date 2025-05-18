package io.gromif.astracrypt.files.domain.usecase.data

import io.gromif.astracrypt.files.domain.repository.DataSource
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase

class InvalidateDataSourceUseCase<T>(
    private val dataSource: DataSource<T>,
    private val getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase
) {

    operator fun invoke() {
        val folderId = getCurrentNavFolderUseCase().id
        dataSource.setFolderId(folderId)
        dataSource.invalidate()
    }

}
