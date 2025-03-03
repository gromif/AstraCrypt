package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRecentItemsUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Item>> {
        return settingsRepository.getAeadInfoFlow().flatMapLatest {
            repository.getRecentFilesList(it)
        }
    }

}