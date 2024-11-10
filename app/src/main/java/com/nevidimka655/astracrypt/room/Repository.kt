package com.nevidimka655.astracrypt.room

import android.content.Context
import androidx.room.Room
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.entities.DetailsFolderContent
import com.nevidimka655.astracrypt.entities.EncryptionInfo
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.AppConfig
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.astracrypt.utils.enums.StorageItemState
import com.nevidimka655.astracrypt.utils.enums.StorageItemType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

object Repository {
    private val database = Room.databaseBuilder(
        Engine.appContext,
        AppDatabase::class.java,
        AppConfig.DB_NAME
    ).build()
    private var databaseExternal: AppDatabase? = null
    private var storage = database.getStorageItemDao()
    private var notes = database.getNotesDao()

    fun switchDatabase(db: AppDatabase?) {
        databaseExternal = db
        storage = db?.getStorageItemDao() ?: database.getStorageItemDao()
        notes = db?.getNotesDao() ?: database.getNotesDao()
    }

    suspend fun createBasicFolders(context: Context, encryptionInfo: EncryptionInfo) = arrayOf(
        R.string.music, R.string.document, R.string.video, R.string.photo
    ).forEach {
        newDirectory(
            encryptionInfo = encryptionInfo,
            directoryName = context.getString(it),
            parentDirectoryId = 0
        )
    }

    suspend fun insert(encryptionInfo: EncryptionInfo, item: StorageItemEntity) = storage.insert(
        RepositoryEncryption.encryptStorageItemEntity(encryptionInfo, item)
    )

    suspend fun insertNote(encryptionInfo: EncryptionInfo, noteItem: NoteItemEntity) = notes.insert(
        RepositoryEncryption.encryptNoteItemEntity(encryptionInfo, noteItem)
    )

    suspend fun updateNote(encryptionInfo: EncryptionInfo, noteItem: NoteItemEntity) = notes.update(
        RepositoryEncryption.encryptNoteItemEntity(encryptionInfo, noteItem)
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

    suspend fun newDirectory(
        encryptionInfo: EncryptionInfo,
        directoryName: String,
        parentDirectoryId: Long?
    ) {
        val item = StorageItemEntity(
            name = directoryName,
            itemType = StorageItemType.Folder,
            parentDirectoryId = parentDirectoryId ?: 0,
            creationTime = System.currentTimeMillis()
        )
        insert(encryptionInfo, item)
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

    suspend fun updateName(
        id: Long,
        encryptionInfo: EncryptionInfo,
        name: String
    ) {
        val newName = if (encryptionInfo.isDatabaseEncrypted) {
            RepositoryEncryption.encryptName(encryptionInfo, name)
        } else name
        storage.updateName(id, newName)
    }

    suspend fun getNoteTextById(encryptionInfo: EncryptionInfo, itemId: Long) = run {
        val text = notes.getTextId(itemId = itemId)
        if (encryptionInfo.isNotesEncrypted) RepositoryEncryption.decryptNoteText(
            encryptionInfo, text
        ) else text
    }

    suspend fun getById(encryptionInfo: EncryptionInfo, itemId: Long) = run {
        val item = storage.getById(itemId)
        if (encryptionInfo.isDatabaseEncrypted) item.copy(
            name = RepositoryEncryption.decryptName(encryptionInfo, item.name),
            thumb = RepositoryEncryption.decryptThumb(encryptionInfo, item.thumb),
            path = RepositoryEncryption.decryptPath(encryptionInfo, item.path),
            flags = RepositoryEncryption.decryptFlags(encryptionInfo, item.flags),
            encryptionType = RepositoryEncryption.decryptEncryptionType(
                encryptionInfo = encryptionInfo,
                itemId = item.id,
                value = item.encryptionType
            ),
            thumbnailEncryptionType = RepositoryEncryption.decryptThumbEncryptionType(
                encryptionInfo = encryptionInfo,
                itemId = item.id,
                value = item.thumbnailEncryptionType
            )
        ) else item
    }

    suspend fun getMaxId() = storage.getMaxId()
    suspend fun getDirIdsList(dirId: Long) = storage.getDirIdsList(dirId)
    suspend fun getFilesCountFlow(dirId: Long) = storage.getFilesCountFlow(dirId)
    suspend fun getListDataToExportFromDir(encryptionInfo: EncryptionInfo, dirId: Long) = run {
        val list = storage.getListDataToExport(dirId)
        if (encryptionInfo.isDatabaseEncrypted) list.map {
            it.copy(
                name = RepositoryEncryption.decryptName(encryptionInfo, it.name),
                path = RepositoryEncryption.decryptPath(encryptionInfo, it.path),
                encryptionType = RepositoryEncryption.decryptEncryptionType(
                    encryptionInfo, it.id, it.encryptionType
                )
            )
        } else list
    }

    suspend fun getDataToExport(encryptionInfo: EncryptionInfo, itemId: Long) = run {
        val item = storage.getDataToExport(itemId)
        if (encryptionInfo.isDatabaseEncrypted) item.copy(
            name = RepositoryEncryption.decryptName(encryptionInfo, item.name),
            path = RepositoryEncryption.decryptPath(encryptionInfo, item.path),
            encryptionType = RepositoryEncryption.decryptEncryptionType(
                encryptionInfo, item.id, item.encryptionType
            )
        ) else item
    }

    suspend fun getName(encryptionInfo: EncryptionInfo, id: Long) = run {
        val name = storage.getName(id)
        if (encryptionInfo.isDatabaseEncrypted) {
            RepositoryEncryption.decryptName(encryptionInfo, name)
        } else name
    }

    suspend fun getMinimalItemsDataInDir(encryptionInfo: EncryptionInfo, dirId: Long) = run {
        val item = storage.getMinimalItemsDataInDir(dirId)
        if (encryptionInfo.isDatabaseEncrypted) item.map {
            it.copy(
                name = RepositoryEncryption.decryptName(encryptionInfo, it.name),
                thumb = RepositoryEncryption.decryptThumb(encryptionInfo, it.thumb),
                path = RepositoryEncryption.decryptPath(encryptionInfo, it.path)
            )
        } else item
    }

    suspend fun getMinimalItemData(encryptionInfo: EncryptionInfo, id: Long) = run {
        val item = storage.getMinimalItemData(id)
        if (encryptionInfo.isDatabaseEncrypted) item.copy(
            name = RepositoryEncryption.decryptName(encryptionInfo, item.name),
            thumb = RepositoryEncryption.decryptThumb(encryptionInfo, item.thumb),
            path = RepositoryEncryption.decryptPath(encryptionInfo, item.path)
        ) else item
    }

    suspend fun getDataForOpening(encryptionInfo: EncryptionInfo, id: Long) = run {
        val item = storage.getDataToOpen(id)
        if (encryptionInfo.isDatabaseEncrypted) item.copy(
            name = RepositoryEncryption.decryptName(encryptionInfo, item.name),
            encryptionType = RepositoryEncryption.decryptEncryptionType(
                encryptionInfo = encryptionInfo,
                itemId = id,
                value = item.encryptionType
            ),
            path = RepositoryEncryption.decryptPath(encryptionInfo, item.path)
        ) else item
    }

    suspend fun getParentDirInfo(encryptionInfo: EncryptionInfo, dirId: Long) =
        storage.getParentDirInfo(dirId)?.run {
            if (encryptionInfo.isDatabaseEncrypted) copy(
                name = RepositoryEncryption.decryptName(
                    encryptionInfo = encryptionInfo,
                    value = this.name
                )
            ) else this
        }

    suspend fun getAbsolutePath(
        encryptionInfo: EncryptionInfo,
        parentDirId: Long,
        childName: String
    ): String {
        var path = "/$childName"
        suspend fun dirIterator(dirId: Long) {
            val dirObj = getParentDirInfo(encryptionInfo, dirId)
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
        dirIdsForSearch: ArrayList<Long>? = null,
        isNameEncrypted: Boolean = false
    ) = storage.listOrderDescAsc(
        parentDirId = if (dirIdsForSearch == null) parentDirectoryId else -1,
        query = searchQuery,
        dirIdsForSearch = dirIdsForSearch ?: arrayListOf(),
        sortingItemType = StorageItemType.Folder.ordinal,
        sortingSecondType = (if (isNameEncrypted) DatabaseColumns.ID
        else DatabaseColumns.Name).ordinal
    )

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

    fun clearAllTables() {
        database.clearAllTables()
    }

}