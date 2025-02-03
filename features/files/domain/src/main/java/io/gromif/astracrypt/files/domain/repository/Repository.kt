package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun get(
        aeadInfo: AeadInfo? = null,
        id: Long,
    ): Item

    suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean = true
    ): List<Long>

    suspend fun insert(
        aeadInfo: AeadInfo? = null,
        parent: Long,
        name: String,
        itemState: ItemState = ItemState.Default,
        itemType: ItemType,
        file: String? = null,
        fileAead: Int = -1,
        preview: String? = null,
        previewAead: Int = -1,
        flags: String? = null,
        creationTime: Long = 0,
        size: Long = 0,
    )

    suspend fun delete(id: Long)

    suspend fun move(ids: List<Long>, parentId: Long)

    suspend fun rename(id: Long, newName: String)

    suspend fun setStarred(ids: List<Long>, state: Boolean)

    suspend fun export(ids: List<Long>, outputPath: String)

    suspend fun exportPrivately(id: Long): String?

    fun getRecentFilesList(): Flow<List<Item>>

    suspend fun getItemDetails(id: Long): ItemDetails

}