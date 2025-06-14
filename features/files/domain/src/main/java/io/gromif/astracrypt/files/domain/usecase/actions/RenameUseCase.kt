package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class RenameUseCase(
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val itemWriter: ItemWriter,
) {

    suspend operator fun invoke(id: Long, newName: String) {
        val targetName = newName.trim()
        NameValidator(targetName)

        val aeadInfo = getAeadInfoUseCase()
        itemWriter.rename(aeadInfo, id, targetName)
    }
}
