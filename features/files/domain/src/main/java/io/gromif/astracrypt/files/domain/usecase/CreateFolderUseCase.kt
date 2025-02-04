package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class CreateFolderUseCase(
    private val repository: Repository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(name: String, parentId: Long?) {
        val targetName = name.trim()
        NameValidator(targetName)

        val aeadInfo = settingsRepository.getAeadInfo()
        repository.insert(
            aeadInfo = aeadInfo,
            name = targetName,
            parent = parentId ?: 0,
            itemType = ItemType.Folder
        )
    }

}