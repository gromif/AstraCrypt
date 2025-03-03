package io.gromif.astracrypt.files.data.db

import androidx.paging.PagingSource
import io.gromif.astracrypt.files.data.db.tuples.DeleteTuple
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.ExportTuple
import io.gromif.astracrypt.files.data.db.tuples.PagerTuple
import io.gromif.astracrypt.files.data.db.tuples.UpdateAeadTuple
import io.gromif.astracrypt.files.data.util.AeadHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.astracrypt.files.domain.model.ItemType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FilesDaoAeadAdapter private constructor(
    private val filesDao: FilesDao,
    private val aeadHandler: AeadHandler,
    private val aeadInfo: AeadInfo,
) : FilesDao {

    class Factory(
        private val filesDao: FilesDao,
        private val aeadHandler: AeadHandler,
    ) {

        fun create(aeadInfo: AeadInfo): FilesDaoAeadAdapter {
            return FilesDaoAeadAdapter(
                filesDao = filesDao,
                aeadHandler = aeadHandler,
                aeadInfo = aeadInfo
            )
        }

    }

    private val databaseMode = aeadInfo.databaseMode

    fun compareAeadInfo(aeadInfo: AeadInfo): Boolean {
        return this.aeadInfo === aeadInfo
    }

    override suspend fun get(id: Long): FilesEntity {
        val filesEntity = filesDao.get(id)
        return if (databaseMode is AeadMode.Template) {
            aeadHandler.decryptFilesEntity(aeadInfo, databaseMode, filesEntity)
        } else filesEntity
    }

    override suspend fun getIdList(
        parent: Long,
        typeFilter: ItemType?,
        excludeFolders: Boolean,
    ): List<Long> {
        return filesDao.getIdList(parent, typeFilter, excludeFolders)
    }

    override suspend fun getDeleteData(id: Long): DeleteTuple {
        val deleteTuple = filesDao.getDeleteData(id)
        return if (databaseMode is AeadMode.Template) {
            aeadHandler.decryptDeleteTuple(aeadInfo, databaseMode, deleteTuple)
        } else deleteTuple
    }

    override suspend fun getDetailsById(id: Long): DetailsTuple {
        val detailsTuple = filesDao.getDetailsById(id)
        return if (databaseMode is AeadMode.Template) {
            aeadHandler.decryptDetailsTuple(aeadInfo, databaseMode, detailsTuple)
        } else detailsTuple
    }

    override suspend fun insert(filesEntity: FilesEntity) {
        val entity = if (databaseMode is AeadMode.Template) {
            aeadHandler.encryptFilesEntity(aeadInfo, databaseMode, filesEntity)
        } else filesEntity
        filesDao.insert(entity)
    }

    override suspend fun delete(id: Long) {
        filesDao.delete(id)
    }

    override suspend fun move(ids: List<Long>, parent: Long) {
        filesDao.move(ids, parent)
    }

    override suspend fun rename(id: Long, name: String) {
        val name = if (databaseMode is AeadMode.Template) {
            aeadHandler.encryptNameIfNeeded(aeadInfo, databaseMode, name)
        } else name
        filesDao.rename(id, name)
    }

    override suspend fun setStarred(id: Long, state: Int) {
        filesDao.setStarred(id, state)
    }

    override suspend fun updateAead(updateAeadTuple: UpdateAeadTuple) {
        val tuple = if (databaseMode is AeadMode.Template) {
            aeadHandler.encryptUpdateAeadTuple(aeadInfo, databaseMode, updateAeadTuple)
        } else updateAeadTuple
        filesDao.updateAead(tuple)
    }

    override suspend fun getExportData(id: Long): ExportTuple {
        val exportTuple = filesDao.getExportData(id)
        return if (databaseMode is AeadMode.Template) {
            aeadHandler.decryptExportTuple(aeadInfo, databaseMode, exportTuple)
        } else exportTuple
    }

    override suspend fun getUpdateAeadTupleList(
        pageSize: Int,
        pageOffset: Int,
    ): List<UpdateAeadTuple> {
        val updateAeadTuple = filesDao.getUpdateAeadTupleList(pageSize, pageOffset)
        return if (databaseMode is AeadMode.Template) updateAeadTuple.map {
            aeadHandler.decryptUpdateAeadTuple(aeadInfo, databaseMode, it)
        } else updateAeadTuple
    }

    override fun getRecentFilesFlow(): Flow<List<FilesEntity>> {
        return filesDao.getRecentFilesFlow().map { list ->
            if (databaseMode is AeadMode.Template) list.map {
                aeadHandler.decryptFilesEntity(aeadInfo, databaseMode, it)
            } else list
        }
    }

    override fun listDefault(
        rootId: Long,
        query: String?,
        rootIdsToSearch: List<Long>,
        sortingItemType: Int,
        sortingSecondType: Int,
    ): PagingSource<Int, PagerTuple> {
        return filesDao.listDefault(
            rootId, query,  rootIdsToSearch, sortingItemType, sortingSecondType
        )
    }

    override fun listStarred(
        query: String?,
        sortingItemType: Int,
        sortingSecondType: Int,
    ): PagingSource<Int, PagerTuple> {
        return filesDao.listStarred(query, sortingItemType, sortingSecondType)
    }

}