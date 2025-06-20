package io.gromif.astracrypt.files.export.model

import androidx.compose.runtime.Immutable

@Immutable
data class Params(
    val isExternalExport: Boolean = true,
    val idList: List<Long>,
    val outputPath: String?,
)
