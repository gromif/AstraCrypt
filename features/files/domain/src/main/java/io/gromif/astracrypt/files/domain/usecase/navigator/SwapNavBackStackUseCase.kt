package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator

class SwapNavBackStackUseCase(
    private val storageNavigator: StorageNavigator
) {

    operator fun invoke(targetBackStack: List<StorageNavigator.Folder>) {
        storageNavigator.swapBackStackWith(targetBackStack)
    }
}
