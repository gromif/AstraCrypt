package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.Repository

class ExportUseCase(
    private val repository: Repository
) {

    suspend operator fun invoke(
        idList: List<Long>,
        outputPath: String
    ) {
        repository.export(
            ids = idList,
            outputPath = outputPath
        )
    }

}