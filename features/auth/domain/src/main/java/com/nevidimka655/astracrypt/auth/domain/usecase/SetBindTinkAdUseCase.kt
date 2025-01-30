package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SetBindTinkAdUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(bind: Boolean, password: String) = coroutineScope {
        launch {
            val auth = getAuthUseCase()
            setAuthUseCase(auth.copy(bindTinkAd = bind))
        }
        launch {
            if (bind) tinkRepository.enableAssociatedDataBind(password = password)
            else tinkRepository.disableAssociatedDataBind()
        }
    }

}