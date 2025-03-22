package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class GetAvatarAeadUseCase(
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(): Int {
        return settingsRepository.getAvatarAead()
    }

}