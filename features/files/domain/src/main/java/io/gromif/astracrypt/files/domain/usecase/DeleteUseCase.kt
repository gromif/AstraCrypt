package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.validation.ValidationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DeleteUseCase(
    private val getAeadInfoUseCase: GetAeadInfoUseCase,
    private val repository: Repository,
) {

    suspend operator fun invoke(ids: List<Long>) = coroutineScope {
        require(ids.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        val aeadInfo = getAeadInfoUseCase()
        ids.forEach {
            launch {
                repository.delete(aeadInfo, it)
            }
        }
    }
}
