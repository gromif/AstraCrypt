package com.nevidimka655.astracrypt.entities

import android.net.Uri

data class ExportUiState(
    val itemsCount: Int = 1,
    val progress: Int = 0,
    val name: String = "",
    val lastOutputFile: Uri? = null
)