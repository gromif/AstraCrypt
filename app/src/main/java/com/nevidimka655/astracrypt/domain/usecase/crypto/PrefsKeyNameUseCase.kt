package com.nevidimka655.astracrypt.domain.usecase.crypto

import io.gromif.crypto.tink.core.hash.Sha384Util
import io.gromif.crypto.tink.domain.keyset.PrefsKeyName
import io.gromif.crypto.tink.encoders.HexEncoder

class PrefsKeyNameUseCase(
    private val hexEncoder: HexEncoder,
    private val sha384Util: Sha384Util
): PrefsKeyName {

    override fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = sha384Util.compute(value = prefsAliasBytes)
        return hexEncoder.encode(bytes = prefsAliasHashBytes)
    }

}