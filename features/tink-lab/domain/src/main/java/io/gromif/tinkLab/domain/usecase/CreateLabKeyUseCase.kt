package io.gromif.tinkLab.domain.usecase

import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key
import io.gromif.tinkLab.domain.repository.KeyRepository

class CreateLabKeyUseCase(
    private val keyRepository: KeyRepository
) {

    suspend operator fun invoke(
        dataType: DataType,
        aeadType: String
    ): Key {
        return keyRepository.createKey(
            dataType = dataType,
            aeadType = aeadType
        )
    }
}
