package io.gromif.astracrypt.files.domain.repository

interface ItemExporter {

    suspend fun externalExport(
        idList: List<Long>,
        outputPath: String
    )

    suspend fun internalExport(id: Long): String
}
