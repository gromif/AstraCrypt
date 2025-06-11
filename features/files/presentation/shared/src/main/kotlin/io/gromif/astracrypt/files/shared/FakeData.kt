package io.gromif.astracrypt.files.shared

import androidx.paging.PagingData
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

private const val FAKE_LIST_SIZE = 10
private const val FAKE_FOLDERS_COUNT = 4

// fake data for preview
object FakeData {

    fun paging(): Flow<PagingData<Item>> {
        // create pagingData from a list of fake data
        val pagingData = PagingData.Companion.from(fileItems())
        // pass pagingData containing fake data to a MutableStateFlow
        return MutableStateFlow(pagingData)
    }

    fun fileItems(): List<Item> = List(FAKE_LIST_SIZE) {
        var isFolder = it <= FAKE_FOLDERS_COUNT
        Item(
            id = it.toLong(),
            name = "Item $it",
            type = if (isFolder) ItemType.Folder else ItemType.entries.random(),
            isFolder = isFolder,
            isFile = isFolder.not()
        )
    }
}