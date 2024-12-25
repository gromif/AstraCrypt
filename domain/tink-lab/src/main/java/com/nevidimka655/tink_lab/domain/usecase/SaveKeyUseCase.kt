package com.nevidimka655.tink_lab.domain.usecase

import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.model.Repository

class SaveKeyUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        key: Key,
        uriString: String,
        keysetPassword: String
    ) {
        repository.save(key = key, uriString = uriString, keysetPassword = keysetPassword)
    }

}