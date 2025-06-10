package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.Repository
import io.gromif.tinkLab.domain.util.KeyReader

class LoadKeyUseCase(
    private val repository: Repository
) {

    operator fun invoke(path: String, password: String): KeyReader.Result {
        return repository.load(path = path, password = password)
    }
}
