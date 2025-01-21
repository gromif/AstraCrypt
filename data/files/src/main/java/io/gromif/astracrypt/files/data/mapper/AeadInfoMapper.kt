package io.gromif.astracrypt.files.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.domain.model.AeadInfo

class AeadInfoDtoMapper: Mapper<AeadInfo, AeadInfoDto> {
    override fun invoke(item: AeadInfo): AeadInfoDto {
        return AeadInfoDto(
            fileAeadIndex = item.fileAeadIndex,
            previewAeadIndex = item.previewAeadIndex,
            databaseAeadIndex = item.databaseAeadIndex,
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
            fileAeadIndex = item.fileAeadIndex,
            previewAeadIndex = item.previewAeadIndex,
            databaseAeadIndex = item.databaseAeadIndex,
            db = item.databaseAeadIndex != -1,
            name = item.isNameColumnEncrypted,
            preview = item.isPreviewColumnEncrypted,
            file = item.isPathColumnEncrypted,
            flag = item.isFlagColumnEncrypted
        )
    }
}