package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class DeleteUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Long>) {
        repository.delete(ids = ids)
    }

}