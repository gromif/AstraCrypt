package io.gromif.astracrypt.files.data.util.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.model.Item

class FileItemMapper: Mapper<FilesEntity, Item> {
    override fun invoke(item: FilesEntity): Item {
        return Item(
            id = item.id,
            parent = item.parent,
            name = item.name,
            preview = item.preview?.let {
                FileSource(path = it, aeadIndex = item.previewAead)
            },
            file = item.file?.let {
                FileSource(path = it, aeadIndex = item.fileAead)
            },
            size = item.size,
            type = item.type,
            isFolder = item.type == FileType.Folder,
            isFile = item.type != FileType.Folder,
            state = item.state,
        )
    }
}