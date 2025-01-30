package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.AuthType
import com.nevidimka655.astracrypt.auth.domain.repository.SettingsRepository
import com.nevidimka655.astracrypt.auth.domain.service.TinkService

class SetAuthTypeUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
    private val settingsRepository: SettingsRepository,
    private val tinkService: TinkService,
) {

    suspend operator fun invoke(authType: AuthType?, data: String?) {
        val authHash = data?.let { tinkService.computeAuthHash(data = it) }
        val auth = getAuthUseCase()
        settingsRepository.setAuthHash(hash = authHash)
        setAuthUseCase(
            auth = auth.copy(
                type = authType,
                bindTinkAd = authType?.let { auth.bindTinkAd } == true
            )
        )
        if (authType == null) tinkService.disableAssociatedDataBind()
    }

}