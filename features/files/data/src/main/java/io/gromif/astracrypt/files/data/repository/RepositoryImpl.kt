package io.gromif.astracrypt.files.data.repository

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.MinimalTuple
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.ExportData
import io.gromif.astracrypt.files.domain.model.FileItem
import io.gromif.astracrypt.files.domain.model.FileState
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.AeadUtil
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val filesDao: FilesDao,
    private val aeadUtil: AeadUtil,
    private val settingsRepository: SettingsRepository,
    private val fileHandler: FileHandler,
    private val fileItemMapper: Mapper<FilesEntity, FileItem>,
) : Repository {
    private suspend fun encrypt(aeadInfo: AeadInfo, data: String): String =
        aeadUtil.encrypt(aeadIndex = aeadInfo.databaseAeadIndex, data = data)

    private suspend fun decrypt(aeadInfo: AeadInfo, data: String): String =
        aeadUtil.decrypt(aeadIndex = aeadInfo.databaseAeadIndex, data = data)

    override suspend fun get(aeadInfo: AeadInfo?, id: Long): FileItem {
        val aeadInfo = aeadInfo ?: settingsRepository.getAeadInfo()
        var filesEntity = filesDao.get(id = id)

        if (aeadInfo.db) filesEntity.copy(
            name = if (aeadInfo.name) decrypt(aeadInfo, filesEntity.name) else filesEntity.name,
            file = filesEntity.file?.let {
                if (aeadInfo.file) decrypt(aeadInfo, it) else it
            },

            preview = filesEntity.preview?.let {
                if (aeadInfo.preview) decrypt(aeadInfo, it) else it
            },

            flags = filesEntity.flags?.let {
                if (aeadInfo.flag) decrypt(aeadInfo, it) else it
            }
        )
        return fileItemMapper(filesEntity)
    }

    override suspend fun getFolderIds(
        parentId: Long,
        recursively: Boolean,
    ): List<Long> = coroutineScope {
        val idList = mutableListOf<Long>().also { it.add(parentId) }
        val deque = ArrayDeque<Long>().also { it.add(parentId) }

        while (deque.isNotEmpty()) {
            ensureActive()
            val id = deque.removeFirst()
            val innerFolderIds = filesDao.getFolderIds(id)
            idList.addAll(innerFolderIds)
            deque.addAll(innerFolderIds)
        }
        idList.toList()
    }

    private suspend fun getMinimalData(id: Long): MinimalTuple {
        val aeadInfo = settingsRepository.getAeadInfo()
        val tuple = filesDao.getMinimalData(id = id)
        return if (aeadInfo.db) tuple.copy(
            name = if (aeadInfo.name) decrypt(aeadInfo, tuple.name) else tuple.name,
            file = tuple.file?.let {
                if (aeadInfo.file) decrypt(aeadInfo, it) else it
            },
            preview = tuple.preview?.let { if (aeadInfo.preview) decrypt(aeadInfo, it) else it }
        ) else tuple
    }

    override suspend fun insert(
        aeadInfo: AeadInfo?,
        parent: Long,
        name: String,
        fileState: FileState,
        fileType: FileType,
        file: String?,
        fileAead: Int,
        preview: String?,
        previewAead: Int,
        flags: String?,
        creationTime: Long,
        size: Long,
    ) {
        var nameTemp = name
        var fileTemp = file
        var previewTemp = preview
        var flagsTemp = flags

        val aeadInfo = aeadInfo ?: settingsRepository.getAeadInfo()
        if (aeadInfo.db) {
            if (aeadInfo.name) nameTemp = encrypt(aeadInfo, name)
            if (file != null && aeadInfo.file) fileTemp = encrypt(aeadInfo, file)
            if (preview != null && aeadInfo.preview) previewTemp = encrypt(aeadInfo, preview)
            if (flags != null && aeadInfo.flag) flagsTemp = encrypt(aeadInfo, flagsTemp)
        }
        val filesEntity = FilesEntity(
            parent = parent,
            name = nameTemp,
            state = fileState,
            type = fileType,
            file = fileTemp,
            fileAead = aeadInfo.fileAeadIndex,
            preview = previewTemp,
            previewAead = aeadInfo.previewAeadIndex,
            flags = flagsTemp,
            creationTime = creationTime,
            size = size
        )
        filesDao.insert(filesEntity)
    }

    override suspend fun createFolder(name: String, parentId: Long) {
        val aeadInfo = settingsRepository.getAeadInfo()
        var nameTemp = if (aeadInfo.db && aeadInfo.name) encrypt(aeadInfo, name) else name
        val filesEntity = FilesEntity(
            name = nameTemp,
            parent = parentId,
            type = FileType.Folder,
            creationTime = System.currentTimeMillis()
        )
        filesDao.insert(filesEntity)
    }

    override suspend fun delete(ids: List<Long>): Unit = coroutineScope {
        ids.chunked(10).forEach { chunk ->
            chunk.map { currentId ->
                launch {
                    val (id, _, file, preview) = getMinimalData(currentId)
                    filesDao.delete(id)
                    if (file != null) with(fileHandler) {
                        getFilePath(relativePath = file).delete()
                        if (preview != null) getFilePath(relativePath = preview).delete()
                    } else delete(ids = filesDao.getFolderIds(parent = id))
                }
            }.joinAll()
        }
    }

    override suspend fun move(ids: List<Long>, parent: Long) {
        filesDao.move(ids, parent)
    }

    override suspend fun rename(id: Long, newName: String) {
        val aeadInfo = settingsRepository.getAeadInfo()
        val name = if (aeadInfo.db && aeadInfo.name) encrypt(aeadInfo, newName) else newName
        filesDao.rename(id, name)
    }

    override suspend fun setStarred(ids: List<Long>, state: Boolean) = coroutineScope {
        val fileState = if (state) FileState.Starred else FileState.Default
        ids.chunked(10).forEach { chunk ->
            chunk.map { currentId ->
                launch { filesDao.setStarred(id = currentId, state = fileState.ordinal) }
            }.joinAll()
        }
    }

    override suspend fun getFilesCountFlow(dirId: Long): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getListDataToExportFromDir(dirId: Long): List<ExportData> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataToExport(itemId: Long): ExportData {
        TODO("Not yet implemented")
    }
}