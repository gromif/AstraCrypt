package io.gromif.astracrypt.auth.domain.usecase.hint

import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase

class SetHintVisibilityUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
) {

    suspend operator fun invoke(visible: Boolean) {
        val auth = getAuthUseCase()
        setAuthUseCase(auth.copy(hintState = visible))
    }

}