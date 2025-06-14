package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import kotlinx.coroutines.flow.Flow

class GetNavBackStackFlowUseCase(
    private val storageNavigator: StorageNavigator
) {

    operator fun invoke(): Flow<List<StorageNavigator.Folder>> {
        return storageNavigator.getBackStackFlow()
    }
}
