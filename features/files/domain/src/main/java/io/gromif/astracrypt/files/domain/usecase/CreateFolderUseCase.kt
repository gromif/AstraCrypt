package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class CreateFolderUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(name: String, parentId: Long?) {
        val targetName = name.trim()
        NameValidator(targetName)

        repository.insert(
            name = targetName,
            parent = parentId ?: 0,
            itemType = ItemType.Folder
        )
    }

}