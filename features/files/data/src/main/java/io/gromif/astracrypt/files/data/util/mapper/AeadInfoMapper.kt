package io.gromif.astracrypt.files.data.util.mapper

import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.utils.Mapper
import io.gromif.crypto.tink.model.KeysetTemplates

class AeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto> {
    override fun invoke(item: AeadInfo): AeadInfoDto {
        val fileAeadIndex = parseAeadIndex(item.fileMode)
        val previewAeadIndex = parseAeadIndex(item.previewMode)
        val databaseAeadIndex = parseAeadIndex(item.databaseMode)
        return AeadInfoDto(
            fileAeadIndex = fileAeadIndex,
            previewAeadIndex = previewAeadIndex,
            databaseAeadIndex = databaseAeadIndex,
            isNameColumnEncrypted = item.name,
            isPreviewColumnEncrypted = item.preview,
            isPathColumnEncrypted = item.file,
            isFlagColumnEncrypted = item.flag
        )
    }

    private fun parseAeadIndex(aeadMode: AeadMode): Int = when(aeadMode) {
        AeadMode.None -> -1
        is AeadMode.Template -> aeadMode.id
    }

}

class AeadInfoMapper: Mapper<AeadInfoDto, AeadInfo> {
    override fun invoke(item: AeadInfoDto): AeadInfo {
        val fileMode = parseAeadMode(item.fileAeadIndex)
        val previewMode = parseAeadMode(item.previewAeadIndex)
        val databaseMode = parseAeadMode(item.databaseAeadIndex)
        return AeadInfo(
            fileMode = fileMode,
            previewMode = previewMode,
            databaseMode = databaseMode,
            db = databaseMode is AeadMode.Template,
            name = item.isNameColumnEncrypted,
            preview = item.isPreviewColumnEncrypted,
            file = item.isPathColumnEncrypted,
            flag = item.isFlagColumnEncrypted
        )
    }

    private fun parseAeadMode(aeadIndex: Int): AeadMode = when(aeadIndex) {
        -1 -> AeadMode.None
        else -> AeadMode.Template(
            id = aeadIndex,
            name = KeysetTemplates.AEAD.entries[aeadIndex].name
        )
    }
}