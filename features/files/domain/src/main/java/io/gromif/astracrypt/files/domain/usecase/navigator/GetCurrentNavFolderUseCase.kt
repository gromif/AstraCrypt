package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator

class GetCurrentNavFolderUseCase(
    private val storageNavigator: StorageNavigator
) {

    operator fun invoke(): StorageNavigator.Folder {
        return storageNavigator.getCurrentFolder()
    }
}
