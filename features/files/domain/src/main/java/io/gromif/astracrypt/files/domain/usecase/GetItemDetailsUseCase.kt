package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class GetItemDetailsUseCase(
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val itemReader: ItemReader,
) {

    suspend operator fun invoke(id: Long): ItemDetails {
        val aeadInfo = getAeadInfoUseCase()
        val itemDetails = itemReader.getItemDetails(aeadInfo, id)

        NameValidator(itemDetails.itemName)
        if (itemDetails is ItemDetails.File) {
            require(itemDetails.size > -1) { throw ValidationException.InvalidFileSizeException() }
        }

        return itemDetails
    }
}
