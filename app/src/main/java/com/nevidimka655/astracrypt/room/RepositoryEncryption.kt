package com.nevidimka655.astracrypt.room

import androidx.paging.PagingData
import androidx.paging.map
import com.google.crypto.tink.Aead
import com.nevidimka655.astracrypt.room.entities.NoteItemEntity
import com.nevidimka655.astracrypt.room.entities.StorageItemEntity
import com.nevidimka655.astracrypt.utils.AeadManager
import com.nevidimka655.crypto.tink.KeysetFactory
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.toBase64

class RepositoryEncryption(
    private val keysetFactory: KeysetFactory,
    private val aeadManager: AeadManager
) {
    private suspend fun info() = aeadManager.getInfo()
    private suspend fun getDbAead() = keysetFactory.aead(info().database!!.aead).aeadPrimitive()
    private suspend fun getNotesPrimitive() = keysetFactory.aead(info().aeadNotes!!).aeadPrimitive()

    fun decryptNotesPager(pagingData: PagingData<NoteItemListTuple>) = pagingData.map {
        val aeadInfo = info()
        if (aeadInfo.notes) {
            it.copy(
                name = decryptNoteName(it.name),
                textPreview = decryptNoteTextPreview(it.textPreview)
            )
        } else it
    }

    suspend fun decryptPager(pagingData: PagingData<StorageItemListTuple>) = if (info().db) {
        pagingData.map { decryptStorageItemListTuple(it) }
    } else pagingData

    suspend fun decryptStorageItemListTuple(tuple: StorageItemListTuple): StorageItemListTuple {
        val aeadInfo = info()
        return if (aeadInfo.db) tuple.copy(
            name = decryptName(tuple.name),
            thumbnail = decryptThumb(tuple.thumbnail)
        ) else tuple
    }

    suspend fun decrypt(state: Boolean, value: String): String {
        val aeadInfo = info()
        return if (value.isNotEmpty() && aeadInfo.db && state)
            decryptString(getDbAead(), value) else value
    }

    suspend fun decryptName(value: String) = decrypt(info().name, value)
    suspend fun decryptThumb(value: String) = decrypt(info().thumb, value)
    suspend fun decryptPath(value: String) = decrypt(info().path, value)
    suspend fun decryptFlags(value: String) = decrypt(info().flags, value)

    fun decryptString(aead: Aead, value: String): String {
        val base64Decrypted = value.fromBase64()
        return aead.decrypt(base64Decrypted, keysetFactory.associatedData).decodeToString()
    }

    private suspend fun encryptNoteName(value: String?) = value?.run { encrypt(this) }
    private suspend fun encryptNoteTextPreview(value: String?) = value?.run { encrypt(this) }
    private suspend fun encryptNoteText(value: String?) = value?.run { encrypt(this) }

    private suspend fun decryptNoteName(value: String?) =
        value?.run { decryptString(getNotesPrimitive(), this) }

    private suspend fun decryptNoteTextPreview(value: String?) =
        value?.run { decryptString(getNotesPrimitive(), this) }

    suspend fun decryptNoteText(value: String?) =
        value?.run { decryptString(getNotesPrimitive(), this) }

    suspend fun encryptName(value: String) = if (info().name) encrypt(value) else value

    suspend fun encrypt(value: String) = getDbAead().run {
        encrypt(value.toByteArray(), keysetFactory.associatedData).toBase64()
    }

    suspend fun encryptStorageItemEntity(item: StorageItemEntity) = if (info().db) item.copy(
        name = if (info().name) encrypt(item.name) else item.name,
        thumb = if (info().thumb) encrypt(item.thumb) else item.thumb,
        path = if (info().path) encrypt(item.path) else item.path,
        flags = if (info().flags) encrypt(item.flags) else item.flags
    ) else item

    suspend fun encryptNoteItemEntity(item: NoteItemEntity) = if (info().notes) item.copy(
        name = encryptNoteName(item.name),
        textPreview = encryptNoteTextPreview(item.textPreview),
        text = encryptNoteText(item.text)
    ) else item

}