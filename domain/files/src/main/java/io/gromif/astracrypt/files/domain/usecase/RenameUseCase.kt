package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class RenameUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long, newName: String) {
        repository.rename(
            id = id,
            newName = newName.trim()
        )
    }

}