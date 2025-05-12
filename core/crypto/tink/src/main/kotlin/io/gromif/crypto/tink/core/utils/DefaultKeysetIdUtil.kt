package io.gromif.crypto.tink.core.utils

import io.gromif.crypto.tink.core.encoders.Encoder
import io.gromif.crypto.tink.core.hash.HashUtil
import io.gromif.crypto.tink.keyset.KeysetIdUtil

class DefaultKeysetIdUtil(
    private val encoder: Encoder,
    private val hashUtil: HashUtil,
) : KeysetIdUtil {

    override fun compute(
        tag: String,
        associatedData: ByteArray,
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = hashUtil.compute(value = prefsAliasBytes)
        return encoder.encode(bytes = prefsAliasHashBytes)
    }

}