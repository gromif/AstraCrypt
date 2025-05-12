package io.gromif.tink_lab.data.repository

import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.tink_lab.data.util.TextAeadUtil
import io.gromif.tink_lab.domain.model.DataType
import io.gromif.tink_lab.domain.model.EncryptionException
import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.model.Key
import io.gromif.tink_lab.domain.model.Repository
import io.gromif.tink_lab.domain.util.KeyGenerator
import io.gromif.tink_lab.domain.util.KeyReader
import io.gromif.tink_lab.domain.util.KeyWriter
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.security.GeneralSecurityException

private val keysetAssociatedData = "labKey".toByteArray()

class RepositoryImpl(
    private val textAeadUtil: TextAeadUtil,
    private val keysetParser: KeysetParser,
    private val keyGenerator: KeyGenerator,
    private val keyWriter: KeyWriter,
    private val keyReader: KeyReader,
    private val base64Encoder: Base64Encoder
) : Repository {
    private val mutex = Mutex()
    private var keysetHandle: KeysetHandle? = null

    override fun createKey(dataType: DataType, aeadType: String): Key {
        return keyGenerator(dataType = dataType, aeadType = aeadType)
    }

    override fun save(key: Key, path: String, password: String) {
        keyWriter(
            uriString = path,
            key = key,
            keysetPassword = password,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override fun load(path: String, password: String): KeyReader.Result {
        return keyReader(
            uriString = path,
            keysetPassword = password,
            keysetAssociatedData = keysetAssociatedData
        )
    }

    override fun getFileAeadList(): List<String> {
        return KeysetTemplates.Stream.entries
            .filter { it.name.endsWith("MB") }
            .map { it.name.removeSuffix("_1MB").lowercase() }
    }

    override fun getTextAeadList(): List<String> {
        return KeysetTemplates.AEAD.entries.map { it.name.lowercase() }
    }

    override fun isKeysetLoaded(): Boolean {
        return keysetHandle != null
    }

    override suspend fun parseKeyset(rawKeyset: String) = mutex.withLock {
        keysetHandle = keysetParser(serializedKeyset = rawKeyset).also {
            textAeadUtil.initKeyset(it)
        }
    }

    override suspend fun encryptText(
        text: String,
        associatedData: String
    ): EncryptionResult = mutex.withLock {
        try {
            val encryptedBytes = textAeadUtil.encryptBytes(
                associatedData = associatedData.toByteArray(),
                bytes = text.toByteArray()
            )
            val encodedBytes = base64Encoder.encode(encryptedBytes)
            EncryptionResult.Success(text = encodedBytes)
        } catch (e: GeneralSecurityException) {
            EncryptionResult.Error(EncryptionException.EncryptionFailed(exception = e))
        }
    }

    override suspend fun decryptText(
        encryptedText: String,
        associatedData: String
    ): EncryptionResult = mutex.withLock {
        try {
            val decodedBytes = base64Encoder.decode(encryptedText)
            val decryptedBytes = textAeadUtil.decryptBytes(
                associatedData = associatedData.toByteArray(),
                bytes = decodedBytes
            )
            EncryptionResult.Success(text = decryptedBytes.decodeToString())
        } catch (e: GeneralSecurityException) {
            EncryptionResult.Error(EncryptionException.DecryptionFailed(exception = e))
        }
    }
}