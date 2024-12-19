package com.nevidimka655.astracrypt.domain.usecase.crypto

import com.nevidimka655.crypto.tink.domain.model.keyset.MasterKeyName
import com.nevidimka655.crypto.tink.domain.usecase.encoder.HexUseCase
import com.nevidimka655.crypto.tink.domain.usecase.hash.Sha256UseCase

class MasterKeyNameUseCase(
    private val hexUseCase: HexUseCase,
    private val sha256UseCase: Sha256UseCase
): MasterKeyName {

    override suspend fun get(
        tag: String,
        associatedData: ByteArray
    ): String {
        val tagBytes = tag.toByteArray()
        val masterAliasBytes = byteArrayOf(*tagBytes, *associatedData)
        val masterAliasHashBytes = sha256UseCase.compute(value = masterAliasBytes)
        return hexUseCase.encode(bytes = masterAliasHashBytes)
    }

}