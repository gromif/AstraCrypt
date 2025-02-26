package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetRecentItemsUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(): Flow<List<Item>> {
        val aeadInfo = settingsRepository.getAeadInfo()
        return repository.getRecentFilesList(aeadInfo)
    }

}