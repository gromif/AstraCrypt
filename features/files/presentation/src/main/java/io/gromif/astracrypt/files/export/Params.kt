package io.gromif.astracrypt.files.export

import androidx.compose.runtime.Immutable

@Immutable
data class Params(
    val idList: List<Long>,
    val outputPath: String,
)