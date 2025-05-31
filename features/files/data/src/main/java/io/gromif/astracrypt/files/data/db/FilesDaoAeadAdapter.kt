package io.gromif.astracrypt.files.data.db

import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.RenameTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.aead.AbstractAeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Suppress("detekt:LongParameterList")
class FilesDaoAeadAdapter(
    private val filesDao: FilesDao,
    private val filesEntityAeadHandler: AbstractAeadHandler<FilesEntity>,
    private val deleteTupleAeadHandler: AbstractAeadHandler<DeleteTuple>,
    private val detailsTupleAeadHandler: AbstractAeadHandler<DetailsTuple>,
    private val exportTupleAeadHandler: AbstractAeadHandler<ExportTuple>,
    private val renameTupleAeadHandler: AbstractAeadHandler<RenameTuple>,
    private val updateTupleAeadHandler: AbstractAeadHandler<UpdateAeadTuple>,
    private val aeadInfo: AeadInfo,
) : FilesDao by filesDao {

    private val databaseMode = aeadInfo.databaseMode

    fun compareAeadInfo(aeadInfo: AeadInfo): Boolean {
        return this.aeadInfo === aeadInfo
    }

    override suspend fun get(id: Long): FilesEntity {
        val filesEntity = filesDao.get(id)
        return if (databaseMode is AeadMode.Template) {
            filesEntityAeadHandler.decrypt(aeadInfo, databaseMode, filesEntity)
        } else {
            filesEntity
        }
    }

    override suspend fun getDeleteData(id: Long): DeleteTuple {
        val deleteTuple = filesDao.getDeleteData(id)
        return if (databaseMode is AeadMode.Template) {
            deleteTupleAeadHandler.decrypt(aeadInfo, databaseMode, deleteTuple)
        } else {
            deleteTuple
        }
    }

    override suspend fun getDetailsById(id: Long): DetailsTuple {
        val detailsTuple = filesDao.getDetailsById(id)
        return if (databaseMode is AeadMode.Template) {
            detailsTupleAeadHandler.decrypt(aeadInfo, databaseMode, detailsTuple)
        } else {
            detailsTuple
        }
    }

    override suspend fun insert(filesEntity: FilesEntity) {
        val entity = if (databaseMode is AeadMode.Template) {
            filesEntityAeadHandler.encrypt(aeadInfo, databaseMode, filesEntity)
        } else {
            filesEntity
        }
        filesDao.insert(entity)
    }

    override suspend fun rename(renameTuple: RenameTuple) {
        val tuple = if (databaseMode is AeadMode.Template) {
            renameTupleAeadHandler.encrypt(aeadInfo, databaseMode, renameTuple)
        } else {
            renameTuple
        }
        filesDao.rename(tuple)
    }

    override suspend fun updateAead(updateAeadTuple: UpdateAeadTuple) {
        val tuple = if (databaseMode is AeadMode.Template) {
            updateTupleAeadHandler.encrypt(aeadInfo, databaseMode, updateAeadTuple)
        } else {
            updateAeadTuple
        }
        filesDao.updateAead(tuple)
    }

    override suspend fun getExportData(id: Long): ExportTuple {
        val exportTuple = filesDao.getExportData(id)
        return if (databaseMode is AeadMode.Template) {
            exportTupleAeadHandler.decrypt(aeadInfo, databaseMode, exportTuple)
        } else {
            exportTuple
        }
    }

    override suspend fun getUpdateAeadTupleList(
        pageSize: Int,
        pageOffset: Int,
    ): List<UpdateAeadTuple> {
        val updateAeadTuple = filesDao.getUpdateAeadTupleList(pageSize, pageOffset)
        return if (databaseMode is AeadMode.Template) {
            updateAeadTuple.map {
                updateTupleAeadHandler.decrypt(aeadInfo, databaseMode, it)
            }
        } else {
            updateAeadTuple
        }
    }

    override fun getRecentFilesFlow(): Flow<List<FilesEntity>> {
        return filesDao.getRecentFilesFlow().map { list ->
            if (databaseMode is AeadMode.Template) {
                list.map {
                    filesEntityAeadHandler.decrypt(aeadInfo, databaseMode, it)
                }
            } else {
                list
            }
        }
    }
}
