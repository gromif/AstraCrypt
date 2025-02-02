package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.repository.Repository

class GetItemDetailsUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Long): ItemDetails {
        return repository.getItemDetails(id)
    }

}