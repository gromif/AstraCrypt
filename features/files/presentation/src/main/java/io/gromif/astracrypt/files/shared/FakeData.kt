package io.gromif.astracrypt.files.shared

import androidx.paging.PagingData
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

// fake data for preview
internal object FakeData {

    fun paging(): Flow<PagingData<FileItem>> {
        // create pagingData from a list of fake data
        val pagingData = PagingData.from(fileItems())
        // pass pagingData containing fake data to a MutableStateFlow
        return MutableStateFlow(pagingData)
    }

    fun fileItems(): List<FileItem> = List(10) {
        var isFolder = it <= 4
        FileItem(
            id = it.toLong(),
            name = "Item $it",
            type = if (isFolder) FileType.Folder else FileType.entries.random(),
            isFolder = isFolder,
            isFile = isFolder.not()
        )
    }

}