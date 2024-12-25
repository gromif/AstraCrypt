package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.keyset.MasterKeyName
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha256Util

class MasterKeyNameUseCase(
    private val hexUtil: HexUtil,
    private val sha256Util: Sha256Util
): MasterKeyName {

    override suspend fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val masterAliasBytes = byteArrayOf(*tagBytes, *associatedData)
        val masterAliasHashBytes = sha256Util.compute(value = masterAliasBytes)
        return hexUtil.encode(bytes = masterAliasHashBytes)
    }

}