package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetAvatarAeadUsecase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(aead: Int) {
        val oldAead = settingsRepository.getAvatarAead()
        repository.changeAvatarAead(
            oldAead = oldAead,
            newAead = aead
        )
        settingsRepository.setAvatarAead(aead)
    }

}