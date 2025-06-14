package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator

class CloseNavFolderUseCase(
    private val storageNavigator: StorageNavigator
) {

    operator fun invoke() {
        storageNavigator.pop()
    }
}
