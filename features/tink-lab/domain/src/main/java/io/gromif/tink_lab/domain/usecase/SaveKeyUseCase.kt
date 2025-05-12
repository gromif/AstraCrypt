package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.domain.model.Repository

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