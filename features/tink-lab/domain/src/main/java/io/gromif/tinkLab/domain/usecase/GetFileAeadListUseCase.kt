package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.Repository

class GetFileAeadListUseCase(
    private val repository: Repository
) {

    operator fun invoke(): List<String> {
        return repository.getFileAeadList()
    }
}
