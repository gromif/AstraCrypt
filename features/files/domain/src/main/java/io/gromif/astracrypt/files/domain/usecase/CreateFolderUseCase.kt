package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class CreateFolderUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(name: String, parentId: Long?) {
        repository.createFolder(
            name = name.trim(),
            parentId = parentId ?: 0
        )
    }

}