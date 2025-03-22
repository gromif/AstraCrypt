package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetProfileFlowUseCase(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke(): Flow<Profile> {
        return settingsRepository.profileFlow
    }

}