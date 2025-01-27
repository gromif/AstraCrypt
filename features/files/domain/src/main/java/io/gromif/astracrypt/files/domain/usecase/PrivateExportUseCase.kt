package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class PrivateExportUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long): String? {
        return repository.exportPrivately(id)
    }

}