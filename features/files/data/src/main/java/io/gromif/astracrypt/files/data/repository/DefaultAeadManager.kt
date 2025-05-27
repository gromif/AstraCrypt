package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.FilesDaoAeadAdapter
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DefaultAeadManager(
    private val daoManager: DaoManager,
    private val filesDaoAeadAdapterFactory: FilesDaoAeadAdapter.Factory,
): AeadManager {

    override suspend fun changeAead(
        oldAeadInfo: AeadInfo,
        targetAeadInfo: AeadInfo
    ) = coroutineScope {
        val currentFilesDaoAead = daoManager.files(aeadInfo = oldAeadInfo)
        val targetFilesDaoAead = filesDaoAeadAdapterFactory.create(aeadInfo = targetAeadInfo)

        val pageSize = 10
        var offset = 0
        var page: List<UpdateAeadTuple> = listOf()

        suspend fun nextItemsPage(): Boolean {
            page = currentFilesDaoAead.getUpdateAeadTupleList(pageSize, offset)
            offset += page.size
            return page.isNotEmpty()
        }

        while (nextItemsPage()) page.forEach {
            launch {
                targetFilesDaoAead.updateAead(it)
            }
        }
    }

}