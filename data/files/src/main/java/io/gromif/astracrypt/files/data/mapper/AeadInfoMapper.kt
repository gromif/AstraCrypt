package io.gromif.astracrypt.files.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.dto.AeadInfoDto
import io.gromif.astracrypt.files.domain.model.AeadInfo

class AeadInfoMapper: Mapper<AeadInfoDto, AeadInfo> {
    override fun invoke(item: AeadInfoDto): AeadInfo {
        return AeadInfo(
            fileAeadIndex = item.fileAeadIndex,
            previewAeadIndex = item.previewAeadIndex,
            databaseAeadIndex = item.databaseAeadIndex,
            isNameColumnEncrypted = item.isNameColumnEncrypted,
            isPreviewColumnEncrypted = item.isPreviewColumnEncrypted,
            isPathColumnEncrypted = item.isPathColumnEncrypted,
            isFlagColumnEncrypted = item.isFlagColumnEncrypted
        )
    }
}