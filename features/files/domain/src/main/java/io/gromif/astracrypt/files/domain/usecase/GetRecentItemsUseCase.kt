package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRecentItemsUseCase(
    private val getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
    private val repository: Repository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Item>> {
        return getAeadInfoFlowUseCase().flatMapLatest {
            repository.getRecentFilesList(it)
        }
    }
}
