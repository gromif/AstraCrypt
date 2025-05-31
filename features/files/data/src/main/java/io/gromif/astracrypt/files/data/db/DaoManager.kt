package io.gromif.astracrypt.files.data.db

import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DaoManager(
    private val filesDao: FilesDao,
    private val aeadHandler: AeadHandler,
) {
    private val mutex = Mutex()
    private var cachedFilesDaoAeadAdapter: FilesDaoAeadAdapter? = null

    suspend fun files(aeadInfo: AeadInfo): FilesDao = mutex.withLock {
        val cached = cachedFilesDaoAeadAdapter
        if (cached != null && cached.compareAeadInfo(aeadInfo)) return cached

        return FilesDaoAeadAdapter(
            filesDao = filesDao,
            aeadHandler = aeadHandler,
            aeadInfo = aeadInfo
        ).also { cachedFilesDaoAeadAdapter = it }
    }

    fun files(): FilesDao {
        return filesDao
    }
}
