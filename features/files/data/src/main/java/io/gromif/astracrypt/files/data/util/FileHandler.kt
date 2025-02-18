package io.gromif.astracrypt.files.data.util

import com.google.crypto.tink.StreamingAead
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.utils.io.Randomizer
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.extensions.streamingAead
import io.gromif.crypto.tink.model.KeysetTemplates
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.isActive
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class FileHandler(
    private val keysetManager: KeysetManager,
    private val associatedDataManager: AssociatedDataManager,
    private val settingsRepository: SettingsRepository,
    private val randomizer: Randomizer,
    filesDir: File
) {
    private val dataFolder = "$filesDir/data"
    private val newRelativePath get() = "${getRandomFolderName()}/${getRandomFileName()}"
    private val defaultBuffer get() = ByteArray(8 * 1024)

    suspend fun exportFile(
        outputStream: OutputStream,
        relativePath: String,
        aeadIndex: Int
    ): Boolean = coroutineScope {
        val file = getFilePath(relativePath = relativePath)
        val aead = getFileStreamingAead(aeadIndex = aeadIndex)
        val inputStream = getConditionalInputStream(aead = aead, inputStream = file.inputStream())
        copyInputToOutput(input = inputStream, output = outputStream)
        isActive
    }

    suspend fun writeFile(input: InputStream): String? = coroutineScope {
        val fileRelativePath = newRelativePath
        val file = getFilePath(relativePath = fileRelativePath)
        val aead = getFileStreamingAead()
        val outputStream = getConditionalOutputStream(
            aead = aead,
            outputStream = file.outputStream()
        )
        copyInputToOutput(input = input, output = outputStream)
        if (!isActive && file.delete()) null else fileRelativePath
    }

    suspend fun writePreview(bytes: ByteArray): String? = coroutineScope {
        val fileRelativePath = newRelativePath
        val file = getFilePath(relativePath = fileRelativePath)
        val aead = getPreviewStreamingAead()
        val outputStream = getConditionalOutputStream(
            aead = aead,
            outputStream = file.outputStream()
        )
        if (isActive && file.exists()) outputStream.use { it.write(bytes) } else {
            file.delete()
            return@coroutineScope null
        }
        fileRelativePath
    }

    private suspend fun copyInputToOutput(
        input: InputStream,
        output: OutputStream
    ) = coroutineScope {
        val buffer = defaultBuffer
        var loadedSize = input.read(buffer)
        while (isActive && loadedSize != -1) {
            output.write(buffer, 0, loadedSize)
            loadedSize = input.read(buffer)
        }
        output.close()
    }

    private suspend fun getConditionalOutputStream(
        aead: StreamingAead?,
        outputStream: OutputStream
    ): OutputStream = if (aead != null) {
        aead.newEncryptingStream(outputStream, associatedDataManager.getAssociatedData())
    } else outputStream

    private suspend fun getConditionalInputStream(
        aead: StreamingAead?,
        inputStream: InputStream
    ): InputStream = if (aead != null) {
        aead.newDecryptingStream(inputStream, associatedDataManager.getAssociatedData())
    } else inputStream

    private var cachedFileStreamingAead: StreamingAead? = null
    suspend fun getFileStreamingAead(aeadIndex: Int? = null): StreamingAead? {
        return cachedFileStreamingAead ?: run {
            val aeadIndex = aeadIndex ?: settingsRepository.getAeadInfo().fileMode.id
            KeysetTemplates.Stream.entries.getOrNull(aeadIndex)?.let {
                keysetManager.getKeyset(tag = "import_file", keyParams = it.params)
                    .streamingAead()
            }.also { cachedFileStreamingAead = it }
        }
    }

    private var cachedPreviewStreamingAead: StreamingAead? = null
    suspend fun getPreviewStreamingAead(): StreamingAead? {
        return cachedPreviewStreamingAead ?: run {
            val aeadIndex = settingsRepository.getAeadInfo().previewMode.id
            KeysetTemplates.Stream.entries.getOrNull(aeadIndex)?.let {
                keysetManager.getKeyset(tag = "import_preview", keyParams = it.params)
                    .streamingAead()
            }.also { cachedPreviewStreamingAead = it }
        }
    }

    private fun getRandomFileName(): String = randomizer.generateUrlSafeString(6)
    private fun getRandomFolderName(): String = randomizer.generateUrlSafeString(2)

    fun getFilePath(relativePath: String): File {
        return File("$dataFolder/$relativePath").apply {
            parentFile!!.mkdirs()
            createNewFile()
        }
    }

}