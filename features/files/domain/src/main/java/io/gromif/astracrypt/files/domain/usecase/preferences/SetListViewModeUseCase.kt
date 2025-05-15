package io.gromif.astracrypt.files.domain.usecase.preferences

import io.gromif.astracrypt.files.domain.model.ViewMode
import io.gromif.astracrypt.files.domain.repository.SettingsRepository

class SetListViewModeUseCase(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(viewMode: ViewMode) {
        settingsRepository.setViewMode(viewMode)
    }
}
