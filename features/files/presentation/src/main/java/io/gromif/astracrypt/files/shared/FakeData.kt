package io.gromif.astracrypt.files.shared

import androidx.paging.PagingData
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal object FakeData {

    fun paging(): Flow<PagingData<FileItem>> {
        // create list of fake data for preview
        val fakeData = List(10) {
            var isFolder = it <= 4
            FileItem(
                id = it.toLong(),
                name = "Item $it",
                type = if (isFolder) FileType.Folder else FileType.entries.random(),
                isFolder = isFolder,
                isFile = isFolder.not()
            )
        }
        // create pagingData from a list of fake data
        val pagingData = PagingData.from(fakeData)
        // pass pagingData containing fake data to a MutableStateFlow
        return MutableStateFlow(pagingData)
    }

}