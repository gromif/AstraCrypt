package io.gromif.tink_lab.data.service

import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.keyset.parser.KeysetParser
import io.gromif.tink_lab.data.util.TextAeadUtil
import io.gromif.tink_lab.domain.model.EncryptionException
import io.gromif.tink_lab.domain.model.EncryptionResult
import io.gromif.tink_lab.domain.service.AeadTextService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.security.GeneralSecurityException

class DefaultAeadTextService(
    private val defaultDispatcher: CoroutineDispatcher,
    private val textAeadUtil: TextAeadUtil,
    private val keysetParser: KeysetParser,
    private val base64Encoder: Base64Encoder
) : AeadTextService {
    private val mutex = Mutex()

    override suspend fun setKeyset(rawKeyset: String): Unit = mutex.withLock {
        val keyset = withContext(defaultDispatcher) {
            keysetParser(serializedKeyset = rawKeyset)
        }
        textAeadUtil.initKeyset(keyset)
    }

    override suspend fun encryptText(
        text: String,
        associatedData: String
    ): EncryptionResult = withContext(defaultDispatcher) {
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
    ): EncryptionResult = withContext(defaultDispatcher) {
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
