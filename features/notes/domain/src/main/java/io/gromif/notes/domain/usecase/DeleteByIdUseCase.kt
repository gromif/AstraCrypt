package io.gromif.notes.domain.usecase

import io.gromif.notes.domain.repository.Repository

class DeleteByIdUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long) {
        repository.deleteById(id = id)
    }

}