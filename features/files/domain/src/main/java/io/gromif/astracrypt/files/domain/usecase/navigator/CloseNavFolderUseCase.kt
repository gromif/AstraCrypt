package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase

class CloseNavFolderUseCase<T>(
    private val storageNavigator: StorageNavigator,
    private val invalidateDataSourceUseCase: InvalidateDataSourceUseCase<T>
) {

    operator fun invoke() {
        storageNavigator.pop()
        invalidateDataSourceUseCase()
    }
}
