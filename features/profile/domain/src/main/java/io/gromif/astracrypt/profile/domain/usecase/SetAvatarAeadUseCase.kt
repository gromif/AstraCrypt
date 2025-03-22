package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.repository.Repository
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository

class SetAvatarAeadUseCase(
    private val getAvatarAeadUseCase: GetAvatarAeadUseCase,
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(aead: Int) {
        val oldAead = getAvatarAeadUseCase()
        repository.changeAvatarAead(
            oldAead = oldAead,
            newAead = aead
        )
        settingsRepository.setAvatarAead(aead)
    }

}