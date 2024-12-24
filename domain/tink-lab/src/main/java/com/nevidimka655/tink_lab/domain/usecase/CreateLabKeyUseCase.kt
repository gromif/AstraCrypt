package com.nevidimka655.tink_lab.domain.usecase

import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.model.Key


class CreateLabKeyUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        keysetPassword: String,
        dataType: DataType,
        aeadType: String
    ): Key {
        return repository.createKey(
            keysetPassword = keysetPassword,
            dataType = dataType,
            aeadType = aeadType
        )
    }

}