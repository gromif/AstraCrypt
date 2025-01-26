package io.gromif.astracrypt.files.export

import androidx.compose.runtime.Stable

@Stable
data class Params(
    val idList: List<Long>,
    val outputPath: String,
)