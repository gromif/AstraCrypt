package io.gromif.astracrypt.auth.domain.usecase.hint

import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase

class SetHintTextUseCase(
    private val getAuthUseCase: GetAuthUseCase,
    private val setAuthUseCase: SetAuthUseCase,
) {

    suspend operator fun invoke(text: String) {
        val auth = getAuthUseCase()
        setAuthUseCase(auth.copy(hintText = text))
    }

}