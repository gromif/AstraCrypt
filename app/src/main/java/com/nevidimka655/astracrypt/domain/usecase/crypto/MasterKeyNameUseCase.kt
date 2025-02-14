package com.nevidimka655.astracrypt.domain.usecase.crypto

import io.gromif.crypto.tink.core.hash.HashUtil
import io.gromif.crypto.tink.domain.keyset.MasterKeyName
import io.gromif.crypto.tink.encoders.HexEncoder

class MasterKeyNameUseCase(
    private val hexEncoder: HexEncoder,
    private val hashUtil: HashUtil
): MasterKeyName {

    override suspend fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val masterAliasBytes = byteArrayOf(*tagBytes, *associatedData)
        val masterAliasHashBytes = hashUtil.compute(value = masterAliasBytes)
        return hexEncoder.encode(bytes = masterAliasHashBytes)
    }

}