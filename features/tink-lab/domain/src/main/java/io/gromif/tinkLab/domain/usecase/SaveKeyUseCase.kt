package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.model.Repository

class SaveKeyUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        key: Key,
        path: String,
        password: String
    ) {
        repository.save(key = key, path = path, password = password)
    }

}