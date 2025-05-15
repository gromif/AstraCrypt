package io.gromif.astracrypt.auth.domain.usecase.encryption

import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SetBindTinkAdUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
    private val tinkService: TinkService
) {

    suspend operator fun invoke(bind: Boolean, password: String) = coroutineScope {
        launch {
            val auth = getAuthUseCase()
            setAuthUseCase(auth.copy(bindTinkAd = bind))
        }
        launch {
            if (bind) {
                tinkService.enableAssociatedDataBind(password = password)
            } else {
                tinkService.disableAssociatedDataBind()
            }
        }
    }
}
