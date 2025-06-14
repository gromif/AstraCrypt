package io.gromif.crypto.tink.core.utils

import io.gromif.crypto.tink.core.encoders.Encoder
import io.gromif.crypto.tink.core.hash.HashUtil
import io.gromif.crypto.tink.keyset.KeysetIdUtil

class DefaultKeystoreKeysetIdUtil(
    private val encoder: Encoder,
    private val hashUtil: HashUtil
): KeysetIdUtil {

    override fun compute(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val masterAliasBytes = byteArrayOf(*tagBytes, *associatedData)
        val masterAliasHashBytes = hashUtil.compute(value = masterAliasBytes)
        return encoder.encode(bytes = masterAliasHashBytes)
    }

}