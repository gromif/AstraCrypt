package io.gromif.astracrypt.files.domain.usecase.export

import io.gromif.astracrypt.files.domain.repository.ItemExporter

class InternalExportUseCase(
    private val itemExporter: ItemExporter
) {

    suspend operator fun invoke(id: Long): String? {
        return itemExporter.internalExport(id)
    }
}
