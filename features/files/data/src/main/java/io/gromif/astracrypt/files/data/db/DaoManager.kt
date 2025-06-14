package io.gromif.astracrypt.files.data.db

import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.RenameTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Suppress("detekt:LongParameterList")
class DaoManager(
    private val filesDao: FilesDao,
    private val filesEntityAeadHandler: AbstractAeadHandler<FilesEntity>,
    private val deleteTupleAeadHandler: AbstractAeadHandler<DeleteTuple>,
    private val detailsTupleAeadHandler: AbstractAeadHandler<DetailsTuple>,
    private val exportTupleAeadHandler: AbstractAeadHandler<ExportTuple>,
    private val renameTupleAeadHandler: AbstractAeadHandler<RenameTuple>,
    private val updateTupleAeadHandler: AbstractAeadHandler<UpdateAeadTuple>,
) {
    private val mutex = Mutex()
    private var cachedFilesDaoAeadAdapter: FilesDaoAeadAdapter? = null

    suspend fun files(aeadInfo: AeadInfo): FilesDao = mutex.withLock {
        val cached = cachedFilesDaoAeadAdapter
        if (cached != null && cached.compareAeadInfo(aeadInfo)) return cached

        return FilesDaoAeadAdapter(
            filesDao = filesDao,
            filesEntityAeadHandler = filesEntityAeadHandler,
            deleteTupleAeadHandler = deleteTupleAeadHandler,
            detailsTupleAeadHandler = detailsTupleAeadHandler,
            exportTupleAeadHandler = exportTupleAeadHandler,
            renameTupleAeadHandler = renameTupleAeadHandler,
            updateTupleAeadHandler = updateTupleAeadHandler,
            aeadInfo = aeadInfo
        ).also { cachedFilesDaoAeadAdapter = it }
    }

    fun files(): FilesDao {
        return filesDao
    }
}
