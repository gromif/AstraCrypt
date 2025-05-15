package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.files.domain.validation.ValidationException

class ExternalExportUseCase(
    private val itemExporter: ItemExporter
) {

    suspend operator fun invoke(
        idList: List<Long>,
        outputPath: String
    ) {
        require(idList.isNotEmpty()) { throw ValidationException.EmptyIdListException() }

        itemExporter.externalExport(
            idList = idList,
            outputPath = outputPath
        )
    }
}
