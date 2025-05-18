package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase

class SwapNavBackStackUseCase<T>(
    private val storageNavigator: StorageNavigator,
    private val invalidateDataSourceUseCase: InvalidateDataSourceUseCase<T>
) {

    operator fun invoke(targetBackStack: List<StorageNavigator.Folder>) {
        storageNavigator.swapBackStackWith(targetBackStack)
        invalidateDataSourceUseCase()
    }
}
