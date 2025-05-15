package io.gromif.astracrypt.files.domain.repository

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemState
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun get(aeadInfo: AeadInfo, id: Long): Item

    suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean = true
    ): List<Long>

    suspend fun insert(aeadInfo: AeadInfo, importItemDto: ImportItemDto)

    suspend fun delete(aeadInfo: AeadInfo, id: Long)

    suspend fun move(ids: List<Long>, parentId: Long)

    suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    )

    suspend fun setState(id: Long, state: ItemState)

    suspend fun getRecentFilesList(aeadInfo: AeadInfo): Flow<List<Item>>

    suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails

    suspend fun changeAead(
        oldAeadInfo: AeadInfo,
        targetAeadInfo: AeadInfo
    )
}
