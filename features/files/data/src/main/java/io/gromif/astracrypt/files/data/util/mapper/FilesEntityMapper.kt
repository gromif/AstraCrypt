package io.gromif.astracrypt.files.data.util.mapper

import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto

internal fun ImportItemDto.toFilesEntity(aeadInfo: AeadInfo) = FilesEntity(
    parent = parent,
    name = name,
    state = itemState,
    type = itemType,
    file = file,
    fileAead = aeadInfo.fileMode.id,
    preview = preview,
    previewAead = aeadInfo.previewMode.id,
    flags = flags,
    time = creationTime,
    size = size
)