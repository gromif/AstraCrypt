package io.gromif.astracrypt.files.shared

import androidx.paging.PagingData
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

// fake data for preview
internal object FakeData {

    fun paging(): Flow<PagingData<Item>> {
        // create pagingData from a list of fake data
        val pagingData = PagingData.from(fileItems())
        // pass pagingData containing fake data to a MutableStateFlow
        return MutableStateFlow(pagingData)
    }

    fun fileItems(): List<Item> = List(10) {
        var isFolder = it <= 4
        Item(
            id = it.toLong(),
            name = "Item $it",
            type = if (isFolder) ItemType.Folder else ItemType.entries.random(),
            isFolder = isFolder,
            isFile = isFolder.not()
        )
    }
}
