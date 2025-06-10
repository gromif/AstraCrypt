package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.repository.KeyRepository

class SaveKeyUseCase(
    private val keyRepository: KeyRepository
) {

    suspend operator fun invoke(
        key: Key,
        path: String,
        password: String
    ) {
        keyRepository.save(key = key, path = path, password = password)
    }
}
