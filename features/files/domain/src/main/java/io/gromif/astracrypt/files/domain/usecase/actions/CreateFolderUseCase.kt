package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderUseCase
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class CreateFolderUseCase(
    private val getCurrentNavFolderUseCase: GetCurrentNavFolderUseCase,
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(name: String) {
        val folderId = getCurrentNavFolderUseCase().id
        val targetName = name.trim()
        NameValidator(targetName)

        val aeadInfo = getAeadInfoUseCase()

        val importItemDto = ImportItemDto(
            parent = folderId,
            name = targetName,
            itemState = ItemState.Default,
            itemType = ItemType.Folder,
            file = null,
            preview = null,
            flags = null,
            creationTime = 0,
            size = 0
        )

        repository.insert(
            aeadInfo = aeadInfo,
            importItemDto = importItemDto
        )
    }
}
