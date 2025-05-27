package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRecentItemsUseCase(
    private val getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
    private val itemReader: ItemReader,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Item>> {
        return getAeadInfoFlowUseCase().flatMapLatest {
            itemReader.getRecentFilesList(it)
        }
    }
}
