package io.gromif.astracrypt.files.data.repository

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.AeadManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val DEFAULT_PAGE_SIZE = 10

class DefaultAeadManager(
    private val daoManager: DaoManager,
) : AeadManager {

    override suspend fun changeAead(
        oldAeadInfo: AeadInfo,
        targetAeadInfo: AeadInfo
    ) = coroutineScope {
        val currentFilesDaoAead = daoManager.files(aeadInfo = oldAeadInfo)
        val targetFilesDaoAead = daoManager.files(aeadInfo = targetAeadInfo)

        var offset = 0
        var page: List<UpdateAeadTuple> = listOf()

        suspend fun nextItemsPage(): Boolean {
            page = currentFilesDaoAead.getUpdateAeadTupleList(DEFAULT_PAGE_SIZE, offset)
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
