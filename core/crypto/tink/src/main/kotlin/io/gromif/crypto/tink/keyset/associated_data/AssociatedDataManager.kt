package io.gromif.crypto.tink.keyset.associated_data

import android.os.Build
import com.google.crypto.tink.prf.PrfSet
import com.google.crypto.tink.subtle.XChaCha20Poly1305
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom
import kotlin.random.Random

/**
 * Utility class used to manage the global associated data.
 * Associated data is stored in [associatedDataFile].
 */
class AssociatedDataManager(
    private val associatedDataFile: File
) {
    private val mutex = Mutex()
    private var cached: ByteArray? = null

    /**
     * Returns the associated data by reusing the [cached] value or generating a new one.
     */
    suspend fun getAssociatedData() = run {
        cached ?: initAssociatedData().also { cached = it }
    }

    /**
     * Decrypts the [associatedDataFile] data using the specified password,
     * hashed via the specified [prfSet].
     */
    suspend fun decryptWithPassword(password: String, prfSet: PrfSet) = mutex.withLock {
        val aeadKey = prfSet.computePrimary(password.toByteArray(), AEAD_KEY_SIZE)
        val aeadAssociatedData = prfSet.computePrimary(password.toByteArray(), AEAD_NONCE_SIZE)
        val aead = XChaCha20Poly1305(aeadKey)
        val encryptedBytes = associatedDataFile.readBytes()
        cached = aead.decrypt(encryptedBytes, aeadAssociatedData)
    }

    /**
     * Encrypts the [associatedDataFile] data using the specified password,
     * hashed via the specified [prfSet].
     */
    suspend fun encryptWithPassword(password: String, prfSet: PrfSet) = mutex.withLock {
        val aeadKey = prfSet.computePrimary(password.toByteArray(), AEAD_KEY_SIZE)
        val aeadAssociatedData = prfSet.computePrimary(password.toByteArray(), AEAD_NONCE_SIZE)
        val aead = XChaCha20Poly1305(aeadKey)
        val encryptedBytes = aead.encrypt(getAssociatedData(), aeadAssociatedData)
        associatedDataFile.writeBytes(encryptedBytes)
    }

    /**
     * Decrypts the [associatedDataFile] data.
     */
    suspend fun decrypt() = associatedDataFile.writeBytes(getAssociatedData())

    /**
     * Erases the contents of [associatedDataFile].
     */
    fun erase() = RandomAccessFile(associatedDataFile, "rws").use { it.write(generate()) }

    private suspend fun initAssociatedData(): ByteArray = mutex.withLock {
        if (associatedDataFile.exists()) associatedDataFile.readBytes()
        else generate().also {
            associatedDataFile.createNewFile()
            associatedDataFile.writeBytes(it)
        }
    }

    /**
     * Generates a new associated data set.
     */
    private fun generate(): ByteArray {
        val newSize = Random.Default.nextInt(from = 16, until = 49)
        val bytes = ByteArray(newSize)
        run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) SecureRandom.getInstanceStrong()
            else SecureRandom()
        }.nextBytes(bytes)
        return bytes
    }

}

private const val AEAD_KEY_SIZE = 32
private const val AEAD_NONCE_SIZE = 24