package io.gromif.astracrypt.files.domain.usecase.actions

import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.validation.ValidationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SetStateUseCase(
    private val itemWriter: ItemWriter,
) {

    suspend operator fun invoke(ids: List<Long>, itemState: ItemState) = coroutineScope {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        ids.forEach {
            launch {
                itemWriter.setState(it, itemState)
            }
        }
    }
}
