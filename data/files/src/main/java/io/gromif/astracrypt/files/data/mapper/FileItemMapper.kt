package io.gromif.astracrypt.files.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType

class FileItemMapper: Mapper<FilesEntity, FileItem> {
    override fun invoke(item: FilesEntity): FileItem {
        val type = FileType.entries[item.type]
        return FileItem(
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
            type = type,
            isFolder = type == FileType.Folder,
            isFile = type != FileType.Folder,
            state = FileState.entries[item.state],
        )
    }
}