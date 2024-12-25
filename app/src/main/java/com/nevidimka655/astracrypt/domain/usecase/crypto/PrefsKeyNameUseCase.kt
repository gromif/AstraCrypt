package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.keyset.PrefsKeyName
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha384Util

class PrefsKeyNameUseCase(
    private val hexUtil: HexUtil,
    private val sha384Util: Sha384Util
): PrefsKeyName {

    override fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val prefsAliasBytes = byteArrayOf(*associatedData, *tagBytes)
        val prefsAliasHashBytes = sha384Util.compute(value = prefsAliasBytes)
        return hexUtil.encode(bytes = prefsAliasHashBytes)
    }

}