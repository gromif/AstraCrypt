package io.gromif.astracrypt.files.domain.model

data class AeadInfo(
    val fileMode: AeadMode = AeadMode.None,
    val previewMode: AeadMode = AeadMode.None,
    val databaseMode: AeadMode = AeadMode.None,
    val name: Boolean = false,
    val preview: Boolean = false,
    val file: Boolean = false,
    val flag: Boolean = false
)