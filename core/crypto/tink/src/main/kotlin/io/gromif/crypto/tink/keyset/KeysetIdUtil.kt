package io.gromif.crypto.tink.keyset

/**
 * Defines the process for computing a unique keyset ID.
 */
interface KeysetIdUtil {

    fun compute(
        tag: String,
        associatedData: ByteArray
    ): String

}