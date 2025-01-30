package com.nevidimka655.astracrypt.domain.usecase.crypto

import io.gromif.crypto.tink.core.encoders.HexUtil
import io.gromif.crypto.tink.core.hash.Sha256Util
import io.gromif.crypto.tink.domain.keyset.MasterKeyName

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