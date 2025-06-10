package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.model.Repository

class CreateLabKeyUseCase(
    private val repository: Repository
) {

    operator fun invoke(
        dataType: DataType,
        aeadType: String
    ): Key {
        return repository.createKey(
            dataType = dataType,
            aeadType = aeadType
        )
    }

}