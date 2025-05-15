package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException

class MoveUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Long>, parentId: Long) {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        repository.move(
            ids = ids,
            parentId = parentId
        )
    }
}
