package com.nevidimka655.astracrypt.data.database

import com.google.crypto.tink.Aead
import com.nevidimka655.astracrypt.data.crypto.AeadManager
import com.nevidimka655.astracrypt.data.database.entities.StorageItemEntity
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.toBase64
import com.nevidimka655.notes.data.database.NoteItemEntity

class RepositoryEncryption(
    private val keysetManager: KeysetManager,
    private val aeadManager: AeadManager
) {
    private suspend fun info() = aeadManager.getInfo()
    private suspend fun getDbAead() = keysetManager.aead(info().database!!.aead).aeadPrimitive()
    private suspend fun getNotesPrimitive() = keysetManager.aead(info().aeadNotes!!).aeadPrimitive()

    /*fun decryptNotesPager(pagingData: PagingData<NotesPagerTuple>) = pagingData.map {
        val aeadInfo = info()
        if (aeadInfo.notes) {
            it.copy(
                name = decryptNoteName(it.name),
                textPreview = decryptNoteTextPreview(it.textPreview)
            )
        } else it
    }*/

    suspend fun decryptPagerTuple(tuple: PagerTuple) = tuple.copy(
        name = decryptName(tuple.name),
        preview = decryptPreview(tuple.preview)
    )

    suspend fun decrypt(state: Boolean, value: String?): String? {
        val aeadInfo = info()
        return if (!value.isNullOrEmpty() && aeadInfo.db && state)
            decryptString(getDbAead(), value) else value
    }

    suspend fun decryptName(value: String) = decrypt(info().name, value)!!
    suspend fun decryptPreview(value: String?) = decrypt(info().thumb, value)
    suspend fun decryptPath(value: String) = decrypt(info().path, value)
    suspend fun decryptFlags(value: String) = decrypt(info().flags, value)

    fun decryptString(aead: Aead, value: String): String {
        val base64Decrypted = value.fromBase64()
        return aead.decrypt(base64Decrypted, keysetManager.associatedData).decodeToString()
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
        encrypt(value.toByteArray(), keysetManager.associatedData).toBase64()
    }

    suspend fun encryptStorageItemEntity(item: StorageItemEntity) = if (info().db) item.copy(
        name = if (info().name) encrypt(item.name) else item.name,
        //preview = if (info().thumb) encrypt(item.preview) else item.preview,
        path = if (info().path) encrypt(item.path) else item.path,
        flags = if (info().flags) encrypt(item.flags) else item.flags
    ) else item

    suspend fun encryptNoteItemEntity(item: NoteItemEntity) = if (info().notes) item.copy(
        name = encryptNoteName(item.name),
        textPreview = encryptNoteTextPreview(item.textPreview),
        text = encryptNoteText(item.text)
    ) else item

}