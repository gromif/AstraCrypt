package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class SetStarredUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(ids: List<Long>, state: Boolean) {
        repository.setStarred(
            ids = ids,
            state = state
        )
    }

}