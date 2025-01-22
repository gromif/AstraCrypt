package io.gromif.astracrypt.files.domain.model

data class AeadInfo(
    val fileAeadIndex: Int = -1,
    val previewAeadIndex: Int = -1,
    val databaseAeadIndex: Int = -1,
    val db: Boolean = false,
    val name: Boolean = false,
    val preview: Boolean = false,
    val file: Boolean = false,
    val flag: Boolean = false
)