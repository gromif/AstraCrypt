package io.gromif.astracrypt.files.data.util

import com.google.crypto.tink.StreamingAead
import com.nevidimka655.astracrypt.utils.io.Randomizer
import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.streamingAead
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
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

    suspend fun writeFile(input: InputStream): String? = coroutineScope {
        val fileRelativePath = newRelativePath
        val file = getFilePath(relativePath = fileRelativePath)
        val aead = getFileStreamingAead()
        val outputStream = getConditionalOutputStream(
            aead = aead,
            outputStream = file.outputStream()
        )

        val buffer = ByteArray(8 * 1024)
        var loadedSize = input.read(buffer)
        while (isActive && loadedSize != -1) {
            outputStream.write(buffer, 0, loadedSize)
            loadedSize = input.read(buffer)
        }
        outputStream.close()

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

    private suspend fun getConditionalOutputStream(
        aead: StreamingAead?,
        outputStream: OutputStream
    ): OutputStream = if (aead != null) {
        aead.newEncryptingStream(outputStream, associatedDataManager.getAssociatedData())
    } else outputStream

    private var cachedFileStreamingAead: StreamingAead? = null
    suspend fun getFileStreamingAead(): StreamingAead? {
        return cachedFileStreamingAead ?: run {
            val aeadIndex = settingsRepository.getAeadInfo().fileAeadIndex
            KeysetTemplates.Stream.entries.getOrNull(aeadIndex)?.let {
                keysetManager.getKeyset(tag = "import_file", keyParams = it.params)
                    .streamingAead()
            }.also { cachedFileStreamingAead = it }
        }
    }

    private var cachedPreviewStreamingAead: StreamingAead? = null
    suspend fun getPreviewStreamingAead(): StreamingAead? {
        return cachedPreviewStreamingAead ?: run {
            val aeadIndex = settingsRepository.getAeadInfo().previewAeadIndex
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