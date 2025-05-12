package io.gromif.tink_lab.presentation.files

import androidx.compose.runtime.Stable

@Stable
internal data class FilesScreenState(
    val associatedData: String = "PREVIEW_AD",
    val source: String = "SOURCE",
    val destination: String = "Destination",
    val processingState: Boolean = false,
)
