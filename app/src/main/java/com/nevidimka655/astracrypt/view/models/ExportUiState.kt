package com.nevidimka655.astracrypt.view.models

data class ExportUiState(
    val isDone: Boolean = false,
    val itemsCount: Int = 1,
    val progress: Int = 0,
    val name: String = ""
)