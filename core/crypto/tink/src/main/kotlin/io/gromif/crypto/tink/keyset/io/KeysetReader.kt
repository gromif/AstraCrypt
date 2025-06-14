package io.gromif.crypto.tink.keyset.io

/**
 * Defines the process for reading a keyset in raw format.
 */
interface KeysetReader {

    suspend fun read(key: String): String?

}