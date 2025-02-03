package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException

class SetStarredUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(ids: List<Long>, state: Boolean) {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        repository.setStarred(ids, state)
    }

}