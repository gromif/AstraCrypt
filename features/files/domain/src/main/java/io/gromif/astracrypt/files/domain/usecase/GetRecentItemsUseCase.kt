package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetRecentItemsUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<List<FileItem>> {
        return repository.getRecentFilesList()
    }

}