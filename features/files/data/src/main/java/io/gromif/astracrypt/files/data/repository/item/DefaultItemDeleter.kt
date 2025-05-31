package io.gromif.astracrypt.files.data.repository.item

import io.gromif.astracrypt.files.data.db.DaoManager
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.repository.item.ItemDeleter

class DefaultItemDeleter(
    private val daoManager: DaoManager,
    private val fileHandler: FileHandler,
) : ItemDeleter {

    override suspend fun delete(
        aeadInfo: AeadInfo,
        id: Long
    ) {
        val filesDaoAead = daoManager.files(aeadInfo)
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, file, preview) = filesDaoAead.getDeleteData(currentId)
            filesDaoAead.delete(id)
            if (file != null) {
                with(fileHandler) {
                    getFilePath(relativePath = file).delete()
                    if (preview != null) getFilePath(relativePath = preview).delete()
                }
            } else {
                val innerIdList = filesDaoAead.getIdList(parent = id)
                deque.addAll(innerIdList)
            }
        }
    }
}
