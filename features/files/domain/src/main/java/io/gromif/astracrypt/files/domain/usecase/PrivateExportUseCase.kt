package io.gromif.astracrypt.files.domain.usecase

import io.gromif.astracrypt.files.domain.repository.ItemExporter

class PrivateExportUseCase(
    private val itemExporter: ItemExporter
) {

    suspend operator fun invoke(id: Long): String? {
        return itemExporter.internalExport(id)
    }
}
