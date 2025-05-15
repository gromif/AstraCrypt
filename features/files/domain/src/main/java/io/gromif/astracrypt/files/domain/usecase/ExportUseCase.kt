package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.validation.ValidationException

class ExportUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(
        idList: List<Long>,
        outputPath: String
    ) {
        require(idList.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        repository.export(
            idList = idList,
            outputPath = outputPath
        )
    }
}
