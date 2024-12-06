package com.nevidimka655.astracrypt.room

import androidx.paging.PagingData
import androidx.paging.map
import com.google.crypto.tink.Aead
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.EncryptionManager
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.crypto.tink.IntEncode
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetGroupId
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.toBase64

class RepositoryEncryption(
    private val keysetFactory: KeysetFactory,
    private val encryptionManager: EncryptionManager
) {
    private suspend fun info() = encryptionManager.getInfo()

    private suspend fun getDatabaseKeysetHandle() = keysetFactory.aead(
        KeysetTemplates.AEAD.entries[info().databaseEncryptionOrdinal]
    )

    private suspend fun getDatabasePrimitive() = getDatabaseKeysetHandle().aeadPrimitive()

    private suspend fun dbKeyId() = getDatabaseKeysetHandle().primary.id

    private suspend fun getNotesKeysetHandle() = keysetFactory.aead(
        KeysetTemplates.AEAD.entries[info().notesEncryptionOrdinal],
        keysetGroupId = KeysetGroupId.AEAD_NOTES
    )

    private suspend fun getNotesPrimitive() = getNotesKeysetHandle().aeadPrimitive()

    fun decryptNotesPager(pagingData: PagingData<NoteItemListTuple>) = pagingData.map {
        val encryptionInfo = info()
        if (encryptionInfo.notes) {
            it.copy(
                name = decryptNoteName(it.name),
                textPreview = decryptNoteTextPreview(it.textPreview)
            )
        } else it
    }

    suspend fun decryptPager(
        pagingData: PagingData<StorageItemListTuple>
    ) = if (info().db) pagingData.map {
        decryptStorageItemListTuple(it)
    } else pagingData

    suspend fun decryptStorageItemListTuple(tuple: StorageItemListTuple): StorageItemListTuple {
        val encryptionInfo = info()
        return if (encryptionInfo.db) tuple.copy(
            name = decryptName(tuple.name),
            thumbnail = decryptThumb(tuple.thumbnail),
            thumbnailEncryptionType = decryptThumbEncryptionType(
                itemId = tuple.id,
                value = tuple.thumbnailEncryptionType
            )
        ) else tuple
    }

    suspend fun decryptName(value: String): String {
        val encryptionInfo = info()
        return if (value.isNotEmpty()
            && encryptionInfo.db
            && encryptionInfo.name
        ) decryptStringField(getDatabasePrimitive(), value) else value
    }

    suspend fun decryptThumb(value: String): String {
        val encryptionInfo = info()
        return if (value.isNotEmpty()
            && encryptionInfo.db
            && encryptionInfo.thumb
        ) decryptStringField(getDatabasePrimitive(), value) else value
    }

    suspend fun decryptPath(value: String): String {
        val encryptionInfo = info()
        return if (value.isNotEmpty()
            && encryptionInfo.db
            && encryptionInfo.path
        ) decryptStringField(getDatabasePrimitive(), value) else value
    }

    suspend fun decryptFlags(value: String): String {
        val encryptionInfo = info()
        return if (value.isNotEmpty()
            && encryptionInfo.db
            && encryptionInfo.flags
        ) decryptStringField(getDatabasePrimitive(), value) else value
    }

    suspend fun decryptThumbEncryptionType(itemId: Long, value: Int): Int {
        val encryptionInfo = info()
        return if (encryptionInfo.db
            && encryptionInfo.thumbEncryptionType
        ) decryptIntField(
            dbKeyId(),
            itemId.toInt() * DatabaseColumns.ThumbEncryptionType.ordinal,
            value
        ) else value
    }

    suspend fun decryptEncryptionType(itemId: Long, value: Int): Int {
        val info = this@RepositoryEncryption.info()
        return if (info.db && info.encryptionType) decryptIntField(
            dbKeyId(),
            itemId.toInt() * DatabaseColumns.EncryptionType.ordinal,
            value
        ) else value

    }

    fun decryptStringField(aead: Aead, value: String): String {
        val base64Decrypted = value.fromBase64()
        return aead.decrypt(base64Decrypted, keysetFactory.associatedData).decodeToString()
    }

    fun decryptIntField(
        key: Int,
        associatedLong: Int,
        value: Int
    ) = IntEncode.decode(key, associatedLong, value)

    suspend fun encryptName(value: String): String {
        val encryptionInfo = info()
        return if (encryptionInfo.db
            && encryptionInfo.name
        ) encryptStringField(getDatabasePrimitive(), value)
        else value
    }

    private suspend fun encryptNoteName(value: String?) =
        value?.run { encryptStringField(getNotesPrimitive(), this) }

    private suspend fun encryptNoteTextPreview(value: String?) =
        value?.run { encryptStringField(getNotesPrimitive(), this) }

    private suspend fun encryptNoteText(value: String?) =
        value?.run { encryptStringField(getNotesPrimitive(), this) }

    private suspend fun decryptNoteName(value: String?) =
        value?.run { decryptStringField(getNotesPrimitive(), this) }

    private suspend fun decryptNoteTextPreview(value: String?) =
        value?.run { decryptStringField(getNotesPrimitive(), this) }

    suspend fun decryptNoteText(value: String?) =
        value?.run { decryptStringField(getNotesPrimitive(), this) }

    suspend fun encryptThumb(value: String): String {
        val encryptionInfo = info()
        return if (encryptionInfo.db
            && encryptionInfo.thumb
        ) encryptStringField(getDatabasePrimitive(), value)
        else value
    }

    suspend fun encryptPath(value: String): String {
        val encryptionInfo = info()
        return if (encryptionInfo.db
            && encryptionInfo.path
        ) encryptStringField(getDatabasePrimitive(), value)
        else value
    }

    suspend fun encryptFlags(value: String): String {
        val encryptionInfo = info()
        return if (encryptionInfo.db
            && encryptionInfo.flags
        ) encryptStringField(getDatabasePrimitive(), value)
        else value
    }

    suspend fun encryptThumbEncryptionType(
        itemId: Long, value: Int
    ): Int {
        val info = info()
        return if (info.db && info.thumbEncryptionType) encryptIntField(
            dbKeyId(),
            itemId.toInt() * DatabaseColumns.ThumbEncryptionType.ordinal,
            value
        ) else value
    }

    suspend fun encryptEncryptionType(
        itemId: Long, value: Int
    ): Int {
        val info = info()
        return if (info.db && info.encryptionType) encryptIntField(
            dbKeyId(),
            itemId.toInt() * DatabaseColumns.EncryptionType.ordinal,
            value
        ) else value
    }

    fun encryptStringField(dbPrimitive: Aead, value: String) = dbPrimitive.run {
        encrypt(value.toByteArray(), keysetFactory.associatedData).toBase64()
    }

    fun encryptIntField(key: Int, associatedLong: Int, value: Int) =
        IntEncode.encode(key, associatedLong, value)

    suspend fun encryptStorageItemEntity(
        storageItem: StorageItemEntity
    ) = if (info().db) storageItem.copy(
        name = encryptName(storageItem.name),
        thumb = encryptThumb(storageItem.thumb),
        path = encryptPath(storageItem.path),
        flags = encryptFlags(storageItem.flags),
        encryptionType = encryptEncryptionType(
            storageItem.id, storageItem.encryptionType
        ),
        thumbnailEncryptionType = encryptThumbEncryptionType(
            storageItem.id, storageItem.thumbnailEncryptionType
        )
    ) else storageItem

    suspend fun encryptNoteItemEntity(
        noteItemEntity: NoteItemEntity
    ) = if (info().notes) noteItemEntity.copy(
        name = encryptNoteName(noteItemEntity.name),
        textPreview = encryptNoteTextPreview(noteItemEntity.textPreview),
        text = encryptNoteText(noteItemEntity.text)
    ) else noteItemEntity

}