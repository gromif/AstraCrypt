package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SetStateUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(ids: List<Long>, itemState: ItemState) = coroutineScope {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        ids.forEach {
            launch {
                repository.setState(it, itemState)
            }
        }
    }
}
