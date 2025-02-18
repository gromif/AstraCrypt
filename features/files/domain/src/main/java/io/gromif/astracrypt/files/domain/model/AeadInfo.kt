package io.gromif.astracrypt.files.domain.model

data class AeadInfo(
    val fileMode: Int = -1,
    val previewMode: Int = -1,
    val databaseMode: Int = -1,
    val db: Boolean = false,
    val name: Boolean = false,
    val preview: Boolean = false,
    val file: Boolean = false,
    val flag: Boolean = false
)