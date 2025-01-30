package com.nevidimka655.astracrypt.auth.domain.usecase

import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.repository.Repository
import com.nevidimka655.astracrypt.auth.domain.repository.TinkRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SetBindTinkAdUseCase(
    private val repository: Repository,
    private val tinkRepository: TinkRepository
) {

    suspend operator fun invoke(
        auth: Auth,
        bind: Boolean,
        password: String
    ) = coroutineScope {
        launch { repository.setBindTinkAd(auth = auth, bind = bind) }
        launch {
            if (bind) tinkRepository.enableAssociatedDataBind(password = password)
            else tinkRepository.disableAssociatedDataBind()
        }
    }

}