package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.Repository

class ParseKeysetUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(rawKeyset: String) {
        repository.parseKeyset(rawKeyset)
    }

}