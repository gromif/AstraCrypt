package com.nevidimka655.astracrypt.domain.usecase.crypto

import io.gromif.crypto.tink.core.hash.HashUtil
import io.gromif.crypto.tink.domain.keyset.PrefsKeyName
import io.gromif.crypto.tink.encoders.HexEncoder

class PrefsKeyNameUseCase(
    private val hexEncoder: HexEncoder,
    private val hashUtil: HashUtil
): PrefsKeyName {

    override fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = hashUtil.compute(value = prefsAliasBytes)
        return hexEncoder.encode(bytes = prefsAliasHashBytes)
    }

}