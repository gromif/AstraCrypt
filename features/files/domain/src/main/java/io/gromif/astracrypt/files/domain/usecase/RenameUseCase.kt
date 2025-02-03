package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class RenameUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(id: Long, newName: String) {
        val targetName = newName.trim()
        NameValidator(targetName)

        repository.rename(id = id, newName = targetName)
    }

}