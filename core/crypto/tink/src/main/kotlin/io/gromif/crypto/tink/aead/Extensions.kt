package io.gromif.crypto.tink.aead

import com.google.crypto.tink.Aead
import io.gromif.crypto.tink.core.encoders.Encoder

/**
 * Encrypts the given [value] using the specified [associatedData]
 * and encodes the result using the specified [encoder].
 */
fun Aead.encryptAndEncode(
    value: String,
    associatedData: ByteArray,
    encoder: Encoder,
): String {
    val encryptedBytes = encrypt(value.toByteArray(), associatedData)
    return encoder.encode(bytes = encryptedBytes)
}

/**
 * Decodes the given [value] using the specified [encoder]
 * and decrypts the result using the specified [associatedData].
 */
fun Aead.decodeAndDecrypt(
    value: String,
    associatedData: ByteArray,
    encoder: Encoder,
): String {
    val encryptedBytes = encoder.decode(value = value)
    return decrypt(encryptedBytes, associatedData).decodeToString()
}