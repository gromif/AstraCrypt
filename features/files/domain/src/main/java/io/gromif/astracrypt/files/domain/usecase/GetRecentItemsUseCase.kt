package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.Repository
import kotlinx.coroutines.flow.Flow

class GetRecentItemsUseCase(
    private val repository: Repository
) {

    operator fun invoke(): Flow<List<Item>> {
        return repository.getRecentFilesList()
    }

}