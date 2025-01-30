package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.SkinType
import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.auth.domain.service.TinkService

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