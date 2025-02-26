package io.gromif.astracrypt.files.dto

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import kotlinx.serialization.Serializable

@Serializable
internal data class AeadInfoDto(
    val fileMode: Int,
    val previewMode: Int,
    val databaseMode: Int,
    val name: Boolean,
    val preview: Boolean,
    val file: Boolean,
    val flag: Boolean,
) {

    fun toDomain(): AeadInfo = AeadInfo(
        fileMode = parseAeadMode(fileMode),
        previewMode = parseAeadMode(previewMode),
        databaseMode = parseAeadMode(databaseMode),
        name = name,
        preview = preview,
        file = file,
        flag = flag
    )

    private fun parseAeadMode(id: Int): AeadMode = when {
        id == -1 -> AeadMode.None
        else -> AeadMode.Template(id, "")
    }

}

internal fun AeadInfo.toDto(): AeadInfoDto = AeadInfoDto(
    fileMode = fileMode.id,
    previewMode = previewMode.id,
    databaseMode = databaseMode.id,
    name = name,
    preview = preview,
    file = file,
    flag = flag
)