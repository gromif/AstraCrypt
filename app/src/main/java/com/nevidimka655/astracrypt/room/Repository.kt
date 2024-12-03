package com.nevidimka655.astracrypt.room

import androidx.paging.PagingSource
import com.nevidimka655.astracrypt.model.DetailsFolderContent
import com.nevidimka655.astracrypt.room.daos.NotesDao
import com.nevidimka655.astracrypt.room.daos.StorageItemDao
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.astracrypt.utils.enums.StorageItemState
import com.nevidimka655.astracrypt.utils.enums.StorageItemType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

class Repository(
    private val repositoryEncryption: RepositoryEncryption,
    private val encryptionManager: EncryptionManager,
    private val storage: StorageItemDao,
    private val notes: NotesDao
) {
    private suspend fun info() = encryptionManager.getInfo()

    suspend fun insert(item: StorageItemEntity) = storage.insert(
        repositoryEncryption.encryptStorageItemEntity(item)
    )

    suspend fun insertNote(noteItem: NoteItemEntity) = notes.insert(
        repositoryEncryption.encryptNoteItemEntity(noteItem)
    )

    suspend fun updateNote(noteItem: NoteItemEntity) = notes.update(
        repositoryEncryption.encryptNoteItemEntity(noteItem)
    )

    suspend fun updateDbEntry(
        id: Long,
        name: String,
        thumb: String,
        path: String,
        encryptionType: Int,
        thumbEncryptionType: Int,
        flags: String
    ) = storage.updateDbEntry(
        DatabaseTransformTuple(id, name, thumb, path, encryptionType, thumbEncryptionType, flags)
    )

    suspend fun updateTransformNotes(id: Long, name: String?, text: String?, textPreview: String?) =
        notes.updateTransform(TransformNotesTuple(id, name, text, textPreview))

    suspend fun newDirectory(directoryName: String, parentDirectoryId: Long?) {
        val item = StorageItemEntity(
            name = directoryName,
            itemType = StorageItemType.Folder,
            parentDirectoryId = parentDirectoryId ?: 0,
            creationTime = System.currentTimeMillis()
        )
        insert(item)
    }

    suspend fun deleteByIds(storageItemIds: ArrayList<Long>) = storage.deleteByIds(storageItemIds)

    suspend fun deleteNoteById(id: Long) = notes.deleteById(id)

    suspend fun setStarred(
        id: Long? = null,
        idsArray: List<Long>? = null,
        state: Boolean
    ) {
        val newState = if (state) StorageItemState.Starred else StorageItemState.Default
        if (id != null) {
            storage.setStarred(
                id = id,
                state = newState
            )
        } else if (idsArray != null) storage.setStarred(
            idsArray = idsArray,
            state = newState
        )
    }

    suspend fun moveItems(idsArray: List<Long>, newDirId: Long) = storage.moveItems(
        idsArray = idsArray,
        newDirId = newDirId
    )

    suspend fun updateName(id: Long, name: String) {
        val newName = if (info().isDatabaseEncrypted) {
            repositoryEncryption.encryptName(name)
        } else name
        storage.updateName(id, newName)
    }

    suspend fun getNoteTextById(itemId: Long) = run {
        val text = notes.getTextId(itemId = itemId)
        if (info().isNotesEncrypted) repositoryEncryption.decryptNoteText(text) else text
    }

    suspend fun getById(itemId: Long) = run {
        val item = storage.getById(itemId)
        if (info().isDatabaseEncrypted) item.copy(
            name = repositoryEncryption.decryptName(item.name),
            thumb = repositoryEncryption.decryptThumb(item.thumb),
            path = repositoryEncryption.decryptPath(item.path),
            flags = repositoryEncryption.decryptFlags(item.flags),
            encryptionType = repositoryEncryption.decryptEncryptionType(
                itemId = item.id,
                value = item.encryptionType
            ),
            thumbnailEncryptionType = repositoryEncryption.decryptThumbEncryptionType(
                itemId = item.id,
                value = item.thumbnailEncryptionType
            )
        ) else item
    }

    suspend fun getMaxId() = storage.getMaxId()
    suspend fun getTypeById(id: Long) = storage.getTypeById(id)
    suspend fun getDirIdsList(dirId: Long) = storage.getDirIdsList(dirId)
    suspend fun getFilesCountFlow(dirId: Long) = storage.getFilesCountFlow(dirId)
    suspend fun getListDataToExportFromDir(dirId: Long) = run {
        val list = storage.getListDataToExport(dirId)
        if (info().isDatabaseEncrypted) list.map {
            it.copy(
                name = repositoryEncryption.decryptName(it.name),
                path = repositoryEncryption.decryptPath(it.path),
                encryptionType = repositoryEncryption.decryptEncryptionType(
                    it.id, it.encryptionType
                )
            )
        } else list
    }

    suspend fun getDataToExport(itemId: Long) = run {
        val item = storage.getDataToExport(itemId)
        if (info().isDatabaseEncrypted) item.copy(
            name = repositoryEncryption.decryptName(item.name),
            path = repositoryEncryption.decryptPath(item.path),
            encryptionType = repositoryEncryption.decryptEncryptionType(
                item.id, item.encryptionType
            )
        ) else item
    }

    suspend fun getName(id: Long) = run {
        val name = storage.getName(id)
        if (info().isDatabaseEncrypted) {
            repositoryEncryption.decryptName(name)
        } else name
    }

    suspend fun getMinimalItemsDataInDir(dirId: Long) = run {
        val item = storage.getMinimalItemsDataInDir(dirId)
        if (info().isDatabaseEncrypted) item.map {
            it.copy(
                name = repositoryEncryption.decryptName(it.name),
                thumb = repositoryEncryption.decryptThumb(it.thumb),
                path = repositoryEncryption.decryptPath(it.path)
            )
        } else item
    }

    suspend fun getMinimalItemData(id: Long) = run {
        val item = storage.getMinimalItemData(id)
        if (info().isDatabaseEncrypted) item.copy(
            name = repositoryEncryption.decryptName(item.name),
            thumb = repositoryEncryption.decryptThumb(item.thumb),
            path = repositoryEncryption.decryptPath(item.path)
        ) else item
    }

    suspend fun getDataForOpening(id: Long) = run {
        val item = storage.getDataToOpen(id)
        if (info().isDatabaseEncrypted) item.copy(
            name = repositoryEncryption.decryptName(item.name),
            encryptionType = repositoryEncryption.decryptEncryptionType(
                itemId = id,
                value = item.encryptionType
            ),
            path = repositoryEncryption.decryptPath(item.path)
        ) else item
    }

    suspend fun getParentDirInfo(dirId: Long) =
        storage.getParentDirInfo(dirId)?.run {
            if (info().isDatabaseEncrypted) copy(
                name = repositoryEncryption.decryptName(value = this.name)
            ) else this
        }

    suspend fun getAbsolutePath(parentDirId: Long, childName: String): String {
        var path = "/$childName"
        suspend fun dirIterator(dirId: Long) {
            val dirObj = getParentDirInfo(dirId)
            if (dirObj != null) {
                path = "/${dirObj.name}$path"
                if (dirObj.parentDirectoryId != 0L) dirIterator(dirObj.parentDirectoryId)
            }
        }
        if (parentDirId != 0L) dirIterator(parentDirId)
        return path
    }

    suspend fun getDatabaseTransformItems(pageSize: Int, pageIndex: Int) =
        storage.getDatabaseTransformItems(pageSize, pageIndex)

    suspend fun getTransformNotesItems(pageSize: Int, pageIndex: Int) =
        notes.getTransformItems(pageSize, pageIndex)

    suspend fun getFolderContent(id: Long): DetailsFolderContent {
        var foldersCount = 0
        var filesCount = 0
        suspend fun dirIterator(dirId: Long) {
            val dirsList = getDirIdsList(dirId)
            filesCount += getFilesCountFlow(dirId)
            foldersCount += dirsList.size
            dirsList.forEach {
                dirIterator(it)
            }
        }
        dirIterator(id)
        return DetailsFolderContent(foldersCount, filesCount)
    }

    fun getRecentFilesFlow() = storage.getRecentFilesFlow()

    fun getList(
        parentDirectoryId: Long,
        searchQuery: String? = null,
        dirIdsForSearch: ArrayList<Long>? = null
    ): PagingSource<Int, StorageItemListTuple> {
        val info = encryptionManager.getCachedInfo()
        val isNameEncrypted = info?.run { isDatabaseEncrypted && isNameEncrypted } == true
        return storage.listOrderDescAsc(
            parentDirId = if (dirIdsForSearch == null) parentDirectoryId else -1,
            query = searchQuery,
            dirIdsForSearch = dirIdsForSearch ?: arrayListOf(),
            sortingItemType = StorageItemType.Folder.ordinal,
            sortingSecondType = (if (isNameEncrypted) DatabaseColumns.ID
            else DatabaseColumns.Name).ordinal
        )
    }

    fun getStarredList(
        searchQuery: String? = null,
        isNameEncrypted: Boolean = false
    ) = storage.listOrderDescAsc(
        isStarredOnly = true,
        query = searchQuery,
        sortingItemType = StorageItemType.Folder.ordinal,
        sortingSecondType = (if (isNameEncrypted) DatabaseColumns.ID
        else DatabaseColumns.Name).ordinal
    )

    fun getNotesList() = notes.listOrderDescAsc()

    @OptIn(FlowPreview::class)
    fun getFilesCountFlow() = storage.getFilesCountFlow().debounce(200)

    suspend fun getFilesCount() = storage.getFilesCount()

}