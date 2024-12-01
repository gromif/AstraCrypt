package com.nevidimka655.astracrypt.features.lab.aead

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.JsonKeysetWriter
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.subtle.AesEaxJce
import com.google.crypto.tink.subtle.AesGcmJce
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.astracrypt.work.LabFilesWorker
import com.nevidimka655.crypto.tink.Hex2
import com.nevidimka655.crypto.tink.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.aeadPrimitive
import com.nevidimka655.crypto.tink.extensions.fromBase64
import com.nevidimka655.crypto.tink.extensions.sha384
import com.nevidimka655.crypto.tink.extensions.toBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

class LabAeadManager(
    private val snackbarChannelMutable: Channel<Int>
) {
    private val worker get() = Engine.workManager
    private var keysetPassword = ""
    private var keysetPasswordSha384 = byteArrayOf()
    private var keysetAltPasswordSha384 = byteArrayOf()
    var dataType = DataType.Text
    var keysetUri: Uri? = null
    var encryptionType = 0

    var filesSourceUri: Uri? = null
    var filesSourceName: String? = null
    var filesDestinationUri: Uri? = null
    var filesDestinationName: String? = null
    var filesWorkerInfoLiveData: LiveData<WorkInfo?>? = null
    val isFilesTabSetupOk
        get() = filesSourceUri != null
                && filesDestinationUri != null
                && filesWorkerInfoLiveData == null

    private val _keyMutableStateFlow = MutableStateFlow<LabKey?>(null)
    val keyStateFlow = _keyMutableStateFlow.asStateFlow()
    val currentLabKey get() = keyStateFlow.value

    private val templateName
        get() = when (dataType) {
            DataType.Text -> KeysetTemplates.AEAD.entries[encryptionType]
            DataType.Files -> KeysetTemplates.Stream.entries[encryptionType]
        }.name

    private val serializePrimitive get() = AesEaxJce(keysetAltPasswordSha384.copyOf(32), 16)
    private val serializeAssociatedData get() = keysetPasswordSha384.reversedArray()
    private val keysetPrimitive get() = AesGcmJce(keysetPasswordSha384.copyOf(32))
    private val keysetAssociatedData get() = keysetAltPasswordSha384.takeLast(27).toByteArray()

    suspend fun decryptLoadedKeyset(contentResolver: ContentResolver) = keysetUri?.let { uri ->
        try {
            contentResolver.openInputStream(uri)?.use {
                val hexEncodedLabKey = it.readBytes().decodeToString()
                val encryptedLabKey = Hex2.decode(Hex2.Modes.A, hexEncodedLabKey)
                val serializedLabKey = serializePrimitive.decrypt(
                    encryptedLabKey, serializeAssociatedData
                ).decodeToString()
                val labKey = Json.decodeFromString<LabKey>(serializedLabKey)
                val decryptedKeysetHandle = KeysetHandle.readWithAssociatedData(
                    JsonKeysetReader.withBytes(Hex2.decode(Hex2.Modes.B, labKey.encryptedKeyset!!)),
                    keysetPrimitive,
                    keysetAssociatedData
                )
                _keyMutableStateFlow.update {
                    labKey.copy(keysetHandle = decryptedKeysetHandle)
                }
                true
            } ?: false
        } catch (e: Exception) {
            e.toString()
            false
        }
    } ?: false

    suspend fun shuffleKey() {
        keysetUri = null
        val template = KeyTemplates.get(templateName)
        val keysetHandle = KeysetHandle.generateNew(template)
        val keysetByteEncryptedOutput = ByteArrayOutputStream()
        keysetHandle.writeWithAssociatedData(
            JsonKeysetWriter.withOutputStream(keysetByteEncryptedOutput),
            keysetPrimitive,
            keysetAssociatedData
        )
        val keyset = Hex2.encode(Hex2.Modes.B, keysetByteEncryptedOutput.toByteArray())
        val labKey = LabKey(
            dataType = dataType.ordinal,
            encryptedKeyset = keyset,
            keysetHandle = keysetHandle
        )
        _keyMutableStateFlow.update { labKey }
    }

    fun saveKeyFile(
        contentResolver: ContentResolver,
        uri: Uri
    ) {
        val mode = "wt"
        contentResolver.openOutputStream(uri, mode)?.use {
            val keyFileJson = Json.encodeToString(currentLabKey!!)
            val encryptedJson = serializePrimitive.encrypt(
                keyFileJson.toByteArray(), serializeAssociatedData
            )
            val keyFileSerialized = Hex2.encode(Hex2.Modes.A, encryptedJson).toByteArray()
            it.write(keyFileSerialized)
        }
    }

    suspend fun decryptText(
        text: String,
        associatedData: String
    ) = try {
        currentLabKey?.keysetHandle?.run {
            aeadPrimitive().decrypt(
                text.fromBase64(),
                associatedData.toByteArray()
            ).decodeToString()
        }
    } catch (e: Exception) {
        snackbarChannelMutable.send(R.string.error)
        null
    }

    suspend fun encryptText(
        text: String,
        associatedData: String
    ) = try {
        currentLabKey?.keysetHandle?.run {
            aeadPrimitive().encrypt(
                text.toByteArray(),
                associatedData.toByteArray()
            ).toBase64()
        }
    } catch (e: Exception) {
        snackbarChannelMutable.send(R.string.error)
        null
    }

    suspend fun startFilesWorker(
        encryptionMode: Boolean,
        associatedData: String
    ) = withContext(Dispatchers.Default) {
        /*if (filesWorkerInfoLiveData != null) return@withContext
        val aeadForKeyset = LabFilesWorker.aeadForKeyset()
        val keysetByteEncryptedOutput = ByteArrayOutputStream()
        currentLabKey!!.keysetHandle!!.writeWithAssociatedData(
            JsonKeysetWriter.withOutputStream(keysetByteEncryptedOutput),
            aeadForKeyset,
            LabFilesWorker.keysetTransportAssociatedData.toByteArray()
        )
        val encryptedKeyset = keysetByteEncryptedOutput.toByteArray().toBase64()
        val encryptedAssociatedData = aeadForKeyset.encrypt(
            associatedData.toByteArray(),
            LabFilesWorker.associatedDataTransportAssociatedData.toByteArray()
        ).toBase64()
        val data = Data.Builder().apply {
            putStringArray(LabFilesWorker.Args.SOURCE_URI_ARRAY, arrayOf(filesSourceUri!!.toString()))
            putString(LabFilesWorker.Args.TARGET_URI, filesDestinationUri!!.toString())
            putString(LabFilesWorker.Args.ENCRYPTED_AD, encryptedAssociatedData)
            putString(LabFilesWorker.Args.ENCRYPTED_KEYSET, encryptedKeyset)
            putBoolean(LabFilesWorker.Args.MODE, encryptionMode)
        }.build()
        val workerRequest = OneTimeWorkRequestBuilder<LabFilesWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
        worker.enqueue(workerRequest)
        filesWorkerInfoLiveData = worker.getWorkInfoByIdLiveData(workerRequest.id)*/
    }

    fun newKeysetPassword(pass: String): Boolean {
        if (keysetPassword != pass) {
            keysetPassword = pass
            keysetPasswordSha384 = pass.sha384()
            val alternativePassword = StringBuilder().apply {
                append(pass.length.toString())
                appendLine(pass.reversed())
                appendLine()
                appendLine()
                append("SAlt")
                append(pass.toBase64())
            }.toString()
            keysetAltPasswordSha384 = alternativePassword.sha384()
            return true
        }
        return false
    }

    @kotlinx.serialization.Serializable
    data class LabKey(
        @SerialName("a")
        val dataType: Int,
        @SerialName("b")
        val encryptedKeyset: String?,

        @kotlinx.serialization.Transient
        val keysetHandle: KeysetHandle? = null,
        @kotlinx.serialization.Transient
        val isDeserialized: Boolean = false
    )

    enum class DataType { Text, Files }

}