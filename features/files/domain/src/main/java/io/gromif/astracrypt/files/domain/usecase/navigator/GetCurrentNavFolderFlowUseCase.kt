package io.gromif.astracrypt.files.domain.usecase.navigator

import io.gromif.astracrypt.files.domain.repository.StorageNavigator
import kotlinx.coroutines.flow.Flow

class GetCurrentNavFolderFlowUseCase(
    private val storageNavigator: StorageNavigator
) {

    operator fun invoke(): Flow<StorageNavigator.Folder> {
        return storageNavigator.getCurrentFolderFlow()
    }
}
