package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import io.gromif.astracrypt.files.domain.validation.validator.NameValidator

class GetItemDetailsUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(id: Long): ItemDetails {
        val itemDetails = repository.getItemDetails(id)

        NameValidator(itemDetails.itemName)
        if (itemDetails is ItemDetails.File) {
            require(itemDetails.size > -1) { throw ValidationException.InvalidFileSizeException() }
        }

        return itemDetails
    }

}