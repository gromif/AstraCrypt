package com.nevidimka655.astracrypt.room

import androidx.paging.PagingData
import androidx.paging.map
import com.google.crypto.tink.Aead
import com.nevidimka655.astracrypt.model.EncryptionInfo
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.crypto.tink.IntEncode
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.KeysetGroupId
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.toBase64

class RepositoryEncryption(
    private val keysetFactory: KeysetFactory
) {
    private fun getDatabaseKeysetHandle(encryptionInfo: EncryptionInfo) = keysetFactory.aead(
        KeysetTemplates.AEAD.entries[encryptionInfo.databaseEncryptionOrdinal]
    )

    private fun getDatabasePrimitive(encryptionInfo: EncryptionInfo) =
        getDatabaseKeysetHandle(encryptionInfo).aeadPrimitive()

    private fun dbKeyId(encryptionInfo: EncryptionInfo) =
        getDatabaseKeysetHandle(encryptionInfo).primary.id

    private fun getNotesKeysetHandle(encryptionInfo: EncryptionInfo) = keysetFactory.aead(
        KeysetTemplates.AEAD.entries[encryptionInfo.notesEncryptionOrdinal],
        keysetGroupId = KeysetGroupId.AEAD_NOTES
    )

    private fun getNotesPrimitive(encryptionInfo: EncryptionInfo) =
        getNotesKeysetHandle(encryptionInfo).aeadPrimitive()

    fun decryptNotesPager(
        encryptionInfo: EncryptionInfo,
        pagingData: PagingData<NoteItemListTuple>
    ) = pagingData.map {
        if (encryptionInfo.isNotesEncrypted) {
            it.copy(
                name = decryptNoteName(encryptionInfo, it.name),
                textPreview = decryptNoteTextPreview(encryptionInfo, it.textPreview)
            )
        } else it
    }

    fun decryptPager(
        encryptionInfo: EncryptionInfo,
        pagingData: PagingData<StorageItemListTuple>
    ) = if (encryptionInfo.isDatabaseEncrypted) pagingData.map {
        decryptStorageItemListTuple(encryptionInfo, it)
    } else pagingData

    fun decryptStorageItemListTuple(
        encryptionInfo: EncryptionInfo,
        tuple: StorageItemListTuple
    ) = if (encryptionInfo.isDatabaseEncrypted) tuple.copy(
        name = decryptName(encryptionInfo, tuple.name),
        thumbnail = decryptThumb(encryptionInfo, tuple.thumbnail),
        thumbnailEncryptionType = decryptThumbEncryptionType(
            encryptionInfo = encryptionInfo,
            itemId = tuple.id,
            value = tuple.thumbnailEncryptionType
        )
    ) else tuple

    fun decryptName(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (value.isNotEmpty()
        && encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isNameEncrypted
    ) decryptStringField(getDatabasePrimitive(encryptionInfo), value) else value

    fun decryptThumb(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (value.isNotEmpty()
        && encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isThumbnailEncrypted
    ) decryptStringField(getDatabasePrimitive(encryptionInfo), value) else value

    fun decryptPath(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (value.isNotEmpty()
        && encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isPathEncrypted
    ) decryptStringField(getDatabasePrimitive(encryptionInfo), value) else value

    fun decryptFlags(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (value.isNotEmpty()
        && encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isFlagsEncrypted
    ) decryptStringField(getDatabasePrimitive(encryptionInfo), value) else value

    fun decryptThumbEncryptionType(
        encryptionInfo: EncryptionInfo,
        itemId: Long,
        value: Int
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isThumbEncryptionTypeEncrypted
    ) decryptIntField(
        dbKeyId(encryptionInfo),
        itemId.toInt() * DatabaseColumns.ThumbEncryptionType.ordinal,
        value
    ) else value

    fun decryptEncryptionType(
        encryptionInfo: EncryptionInfo,
        itemId: Long,
        value: Int
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isEncryptionTypeEncrypted
    ) decryptIntField(
        dbKeyId(encryptionInfo),
        itemId.toInt() * DatabaseColumns.EncryptionType.ordinal,
        value
    ) else value

    fun decryptStringField(aead: Aead, value: String): String {
        val base64Decrypted = value.fromBase64()
        return aead.decrypt(base64Decrypted, keysetFactory.associatedData).decodeToString()
    }

    fun decryptIntField(
        key: Int,
        associatedLong: Int,
        value: Int
    ) = IntEncode.decode(key, associatedLong, value)

    fun encryptName(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isNameEncrypted
    ) encryptStringField(getDatabasePrimitive(encryptionInfo), value)
    else value

    private fun encryptNoteName(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { encryptStringField(getNotesPrimitive(encryptionInfo), this) }

    private fun encryptNoteTextPreview(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { encryptStringField(getNotesPrimitive(encryptionInfo), this) }

    private fun encryptNoteText(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { encryptStringField(getNotesPrimitive(encryptionInfo), this) }

    private fun decryptNoteName(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { decryptStringField(getNotesPrimitive(encryptionInfo), this) }

    private fun decryptNoteTextPreview(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { decryptStringField(getNotesPrimitive(encryptionInfo), this) }

    fun decryptNoteText(
        encryptionInfo: EncryptionInfo,
        value: String?
    ) = value?.run { decryptStringField(getNotesPrimitive(encryptionInfo), this) }

    fun encryptThumb(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isThumbnailEncrypted
    ) encryptStringField(getDatabasePrimitive(encryptionInfo), value)
    else value

    fun encryptPath(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isPathEncrypted
    ) encryptStringField(getDatabasePrimitive(encryptionInfo), value)
    else value

    fun encryptFlags(
        encryptionInfo: EncryptionInfo,
        value: String
    ) = if (encryptionInfo.isDatabaseEncrypted
        && encryptionInfo.isFlagsEncrypted
    ) encryptStringField(getDatabasePrimitive(encryptionInfo), value)
    else value

    fun encryptThumbEncryptionType(
        encryptionInfo: EncryptionInfo,
        itemId: Long,
        value: Int
    ) =
        if (encryptionInfo.isDatabaseEncrypted && encryptionInfo.isThumbEncryptionTypeEncrypted) encryptIntField(
            dbKeyId(encryptionInfo),
            itemId.toInt() * DatabaseColumns.ThumbEncryptionType.ordinal,
            value
        ) else value

    fun encryptEncryptionType(
        encryptionInfo: EncryptionInfo,
        itemId: Long,
        value: Int
    ) =
        if (encryptionInfo.isDatabaseEncrypted && encryptionInfo.isEncryptionTypeEncrypted) encryptIntField(
            dbKeyId(encryptionInfo),
            itemId.toInt() * DatabaseColumns.EncryptionType.ordinal,
            value
        ) else value

    fun encryptStringField(dbPrimitive: Aead, value: String) = dbPrimitive.run {
        encrypt(value.toByteArray(), keysetFactory.associatedData).toBase64()
    }

    fun encryptIntField(
        key: Int,
        associatedLong: Int,
        value: Int
    ) = IntEncode.encode(key, associatedLong, value)

    fun encryptStorageItemEntity(
        encryptionInfo: EncryptionInfo,
        storageItem: StorageItemEntity
    ) = if (encryptionInfo.isDatabaseEncrypted) storageItem.copy(
        name = encryptName(encryptionInfo, storageItem.name),
        thumb = encryptThumb(encryptionInfo, storageItem.thumb),
        path = encryptPath(encryptionInfo, storageItem.path),
        flags = encryptFlags(encryptionInfo, storageItem.flags),
        encryptionType = encryptEncryptionType(
            encryptionInfo, storageItem.id, storageItem.encryptionType
        ),
        thumbnailEncryptionType = encryptThumbEncryptionType(
            encryptionInfo, storageItem.id, storageItem.thumbnailEncryptionType
        )
    ) else storageItem

    fun encryptNoteItemEntity(
        encryptionInfo: EncryptionInfo,
        noteItemEntity: NoteItemEntity
    ) = if (encryptionInfo.isNotesEncrypted) noteItemEntity.copy(
        name = encryptNoteName(encryptionInfo, noteItemEntity.name),
        textPreview = encryptNoteTextPreview(encryptionInfo, noteItemEntity.textPreview),
        text = encryptNoteText(encryptionInfo, noteItemEntity.text)
    ) else noteItemEntity

}