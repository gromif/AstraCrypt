package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase

class SetSkinTypeUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
    private val settingsRepository: SettingsRepository,
    private val tinkService: TinkService
) {

    suspend operator fun invoke(skinType: SkinType?, data: String?) {
        val auth = getAuthUseCase()
        val skinHash = data?.let { tinkService.computeSkinHash(data = it) }
        settingsRepository.setSkinHash(hash = skinHash)
        setAuthUseCase(auth = auth.copy(skinType = skinType))
    }
}
