package io.gromif.astracrypt.files.domain.repository.item

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import kotlinx.coroutines.flow.Flow

interface ItemReader {

    suspend fun get(aeadInfo: AeadInfo, id: Long): Item

    suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean = true
    ): List<Long>

    suspend fun getRecentFilesList(aeadInfo: AeadInfo): Flow<List<Item>>

    suspend fun getItemDetails(aeadInfo: AeadInfo, id: Long): ItemDetails

}
