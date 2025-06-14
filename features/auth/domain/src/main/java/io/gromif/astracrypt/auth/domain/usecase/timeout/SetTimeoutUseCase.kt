package io.gromif.astracrypt.auth.domain.usecase.timeout

import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase

class SetTimeoutUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
) {

    suspend operator fun invoke(timeout: Timeout) {
        val auth = getAuthUseCase()
        setAuthUseCase(auth.copy(timeout = timeout))
    }
}
