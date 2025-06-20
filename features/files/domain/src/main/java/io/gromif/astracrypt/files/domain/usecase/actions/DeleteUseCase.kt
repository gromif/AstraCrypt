package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.repository.item.ItemDeleter
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DeleteUseCase(
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val itemDeleter: ItemDeleter,
) {

    suspend operator fun invoke(ids: List<Long>) = coroutineScope {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        val aeadInfo = getAeadInfoUseCase()
        ids.forEach {
            launch {
                itemDeleter.delete(aeadInfo, it)
            }
        }
    }
}
