package io.gromif.astracrypt.files.data.repository

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.db.FilesEntity
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.db.tuples.MinimalTuple
import io.gromif.astracrypt.files.data.util.AeadUtil
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.model.AeadInfo
import io.gromif.astracrypt.files.domain.model.Item
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.domain.model.ItemState
import io.gromif.astracrypt.files.domain.model.ItemType
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val filesDao: FilesDao,
    private val aeadUtil: AeadUtil,
    private val settingsRepository: SettingsRepository,
    private val fileHandler: FileHandler,
    private val exportUtil: ExportUtil,
    private val itemMapper: Mapper<FilesEntity, Item>,
    private val itemDetailsMapper: Mapper<DetailsTuple, ItemDetails>,
    private val uriMapper: Mapper<String, Uri>
) : Repository {
    private suspend fun encrypt(aeadInfo: AeadInfo, data: String): String =
        aeadUtil.encrypt(aeadIndex = aeadInfo.databaseAeadIndex, data = data)

    private suspend fun decrypt(aeadInfo: AeadInfo, data: String): String =
        aeadUtil.decrypt(aeadIndex = aeadInfo.databaseAeadIndex, data = data)

    override suspend fun get(aeadInfo: AeadInfo?, id: Long): Item {
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
        return itemMapper(filesEntity)
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
            val innerFolderIds = filesDao.getIdList(parent = id, typeFilter = ItemType.Folder)
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
        itemState: ItemState,
        itemType: ItemType,
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
        val time = if (creationTime == 0L) System.currentTimeMillis() else creationTime
        val filesEntity = FilesEntity(
            parent = parent,
            name = nameTemp,
            state = itemState,
            type = itemType,
            file = fileTemp,
            fileAead = aeadInfo.fileAeadIndex,
            preview = previewTemp,
            previewAead = aeadInfo.previewAeadIndex,
            flags = flagsTemp,
            time = time,
            size = size
        )
        filesDao.insert(filesEntity)
    }

    override suspend fun delete(id: Long) {
        val deque = ArrayDeque<Long>().also { it.add(id) }
        while (deque.isNotEmpty()) {
            val currentId = deque.removeFirst()
            val (id, _, file, preview) = getMinimalData(currentId)
            filesDao.delete(id)
            if (file != null) with(fileHandler) {
                getFilePath(relativePath = file).delete()
                if (preview != null) getFilePath(relativePath = preview).delete()
            } else {
                val innerIdList = filesDao.getIdList(parent = id, typeFilter = ItemType.Folder)
                deque.addAll(innerIdList)
            }
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
        val itemState = if (state) ItemState.Starred else ItemState.Default
        ids.chunked(10).forEach { chunk ->
            chunk.map { currentId ->
                launch { filesDao.setStarred(id = currentId, state = itemState.ordinal) }
            }.joinAll()
        }
    }

    override suspend fun export(idList: List<Long>, outputPath: String) {
        val uri = uriMapper(outputPath)
        if (exportUtil.createDocumentFile(uri)) exportUtil.use {
            it.startExternally(idList = idList)
        }
    }

    override suspend fun exportPrivately(id: Long): String? {
        return exportUtil.use {
            it.startPrivately(id)
        }
    }

    override fun getRecentFilesList(): Flow<List<Item>> {
        return filesDao.getRecentFilesFlow().map { list ->
            list.map { itemMapper(it) }
        }
    }

    override suspend fun getItemDetails(id: Long): ItemDetails {
        val aeadInfo = settingsRepository.getAeadInfo()
        var dto = filesDao.getDetailsById(id)
        if (aeadInfo.db) dto = dto.copy(
            name = if (aeadInfo.name) decrypt(aeadInfo, dto.name) else dto.name,
            file = dto.file?.let {
                if (aeadInfo.file) decrypt(aeadInfo, it) else it
            },

            preview = dto.preview?.let {
                if (aeadInfo.preview) decrypt(aeadInfo, it) else it
            },

            flags = dto.flags?.let {
                if (aeadInfo.flag) decrypt(aeadInfo, it) else it
            }
        )
        val itemDetails: ItemDetails
        when (dto.type) {
            ItemType.Folder -> {
                var folderCount = 0
                var filesCount = 0

                val deque = ArrayDeque<Long>()
                deque.add(id)
                while (deque.isNotEmpty()) {
                    val id = deque.removeFirst()
                    val files = filesDao.getIdList(parent = id, excludeFolders = true)
                    filesCount += files.size

                    val folders = filesDao.getIdList(parent = id, typeFilter = dto.type)
                    folderCount += folders.size

                    deque.addAll(folders)
                }

                itemDetails = ItemDetails.Folder(
                    name = dto.name,
                    filesCount = filesCount,
                    foldersCount = folderCount,
                    creationTime = dto.time
                )
            }
            else -> itemDetails = itemDetailsMapper(dto)
        }
        return itemDetails
    }

}