package io.gromif.astracrypt.files.data.util.mapper

import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.utils.Mapper

class AeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto> {
    override fun invoke(item: AeadInfo): AeadInfoDto {
        return AeadInfoDto(
            fileAeadIndex = item.fileMode,
            previewAeadIndex = item.previewMode,
            databaseAeadIndex = item.databaseMode,
            isNameColumnEncrypted = item.name,
            isPreviewColumnEncrypted = item.preview,
            isPathColumnEncrypted = item.file,
            isFlagColumnEncrypted = item.flag
        )
    }
}

class AeadInfoMapper: Mapper<AeadInfoDto, AeadInfo> {
    override fun invoke(item: AeadInfoDto): AeadInfo {
        return AeadInfo(
            fileMode = item.fileAeadIndex,
            previewMode = item.previewAeadIndex,
            databaseMode = item.databaseAeadIndex,
            db = item.databaseAeadIndex != -1,
            name = item.isNameColumnEncrypted,
            preview = item.isPreviewColumnEncrypted,
            file = item.isPathColumnEncrypted,
            flag = item.isFlagColumnEncrypted
        )
    }
}