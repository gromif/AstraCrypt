package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetListViewModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<ViewMode> {
        return settingsRepository.getViewModeFlow()
    }
}
