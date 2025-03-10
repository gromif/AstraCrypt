package io.gromif.astracrypt.files.domain.model

data class AeadInfo(
    val fileMode: AeadMode = AeadMode.Template(id = 6, name = ""),
    val previewMode: AeadMode = AeadMode.Template(id = 7, name = ""),
    val databaseMode: AeadMode = AeadMode.Template(id = 10, name = ""),
    val name: Boolean = false,
    val preview: Boolean = false,
    val file: Boolean = false,
    val flag: Boolean = true
)