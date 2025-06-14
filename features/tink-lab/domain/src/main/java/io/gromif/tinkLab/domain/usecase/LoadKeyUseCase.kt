package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.result.ReadKeyResult
import io.gromif.tinkLab.domain.repository.KeyRepository

class LoadKeyUseCase(
    private val keyRepository: KeyRepository
) {

    suspend operator fun invoke(path: String, password: String): ReadKeyResult {
        return keyRepository.load(path = path, password = password)
    }
}
