package io.gromif.astracrypt.files.data.repository.item

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.util.mapper.toFilesEntity
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ImportItemDto
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter

class DefaultItemWriter(
    private val daoManager: DaoManager,
) : ItemWriter {

    override suspend fun insert(
        aeadInfo: AeadInfo,
        importItemDto: ImportItemDto
    ) {
        val filesEntity = importItemDto.toFilesEntity(aeadInfo)
        daoManager.files(aeadInfo).insert(filesEntity)
    }

    override suspend fun move(ids: List<Long>, parentId: Long) {
        daoManager.files().move(ids, parentId)
    }

    override suspend fun rename(
        aeadInfo: AeadInfo,
        id: Long,
        name: String
    ) {
        daoManager.files(aeadInfo).rename(id, name)
    }

    override suspend fun setState(
        id: Long,
        state: ItemState
    ) {
        daoManager.files().setStarred(id = id, state = state.ordinal)
    }
}
