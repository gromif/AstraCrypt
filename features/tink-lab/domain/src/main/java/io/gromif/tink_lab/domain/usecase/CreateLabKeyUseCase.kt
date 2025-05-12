package io.gromif.tink_lab.domain.usecase

import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.domain.model.Repository

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