package io.gromif.astracrypt.profile.data.util

import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAead
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class FileUtil(
    private val keysetManager: KeysetManager,
    private val associatedDataManager: AssociatedDataManager,
    filesDir: File,
) {
    val privateIconFile = File("$filesDir/user_icon")

    suspend fun write(bytes: ByteArray, aeadTemplate: KeysetTemplates.Stream?) {
        val simpleOutputStream = privateIconFile.outputStream()
        val outputStream = aeadTemplate?.let {
            getStreamingAead(it).newEncryptingStream(
                simpleOutputStream, associatedDataManager.getAssociatedData()
            )
        } ?: simpleOutputStream
        outputStream.use {
            it.write(bytes)
        }
    }

    suspend fun changeAead(oldAead: KeysetTemplates.Stream?, newAead: KeysetTemplates.Stream?) {
        val byteOutputStream = ByteArrayOutputStream()
        openInputStream(oldAead).use {
            byteOutputStream.write(it.readBytes())
        }
        write(
            bytes = byteOutputStream.toByteArray(),
            aeadTemplate = newAead
        )
    }

    suspend fun openInputStream(aeadTemplate: KeysetTemplates.Stream?): InputStream {
        val inputStream = privateIconFile.inputStream()
        return aeadTemplate?.let {
            getStreamingAead(it).newDecryptingStream(
                inputStream,
                associatedDataManager.getAssociatedData()
            )
        } ?: inputStream
    }

    fun recreate() = with(privateIconFile) {
        delete()
        createNewFile()
    }

    private suspend fun getStreamingAead(
        aeadTemplate: KeysetTemplates.Stream,
    ) = keysetManager.getKeyset(tag = KEYSET_TAG, keyParams = aeadTemplate.params).streamingAead()

}

private const val KEYSET_TAG = "PROFILE_ICON_TAG"