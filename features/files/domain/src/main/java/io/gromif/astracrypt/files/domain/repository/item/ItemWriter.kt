package io.gromif.astracrypt.files.domain.repository.item

import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState

interface ItemWriter {

    suspend fun insert(aeadInfo: AeadInfo, importItemDto: ImportItemDto)

    suspend fun move(ids: List<Long>, parentId: Long)

    suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    )

    suspend fun setState(id: Long, state: ItemState)
}
