package com.nevidimka655.tink_lab.domain.usecase

import com.nevidimka655.tink_lab.domain.model.Repository

class GetFileAeadListUseCase(
    private val repository: Repository
) {

    operator fun invoke(): List<String> {
        return repository.getFileAeadList()
    }

}