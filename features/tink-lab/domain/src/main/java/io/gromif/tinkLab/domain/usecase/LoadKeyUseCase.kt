package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.repository.KeyRepository
import io.gromif.tinkLab.domain.util.KeyReader

class LoadKeyUseCase(
    private val keyRepository: KeyRepository
) {

    suspend operator fun invoke(path: String, password: String): KeyReader.Result {
        return keyRepository.load(path = path, password = password)
    }
}
