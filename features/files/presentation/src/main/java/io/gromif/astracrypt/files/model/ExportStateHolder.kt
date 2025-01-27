package io.gromif.astracrypt.files.model

internal data class ExportStateHolder(
    val isDone: Boolean = false,
    val itemsCount: Int = 1,
    val progress: Int = 0,
    val name: String = ""
)