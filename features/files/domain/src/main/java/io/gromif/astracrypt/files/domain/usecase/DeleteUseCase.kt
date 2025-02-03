package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException

class DeleteUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(ids: List<Long>) {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        repository.delete(ids = ids)
    }

}