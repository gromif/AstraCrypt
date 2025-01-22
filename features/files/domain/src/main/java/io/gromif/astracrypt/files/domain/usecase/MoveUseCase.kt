package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class MoveUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Long>, parentId: Long) {
        repository.move(
            ids = ids,
            parentId = parentId
        )
    }

}