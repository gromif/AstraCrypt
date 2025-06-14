package io.gromif.crypto.tink.keyset.serializers

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.TinkProtoKeysetFormat
import io.gromif.crypto.tink.core.encoders.Encoder

class KeysetSerializerWithAead(
    private val encoder: Encoder
) {

    operator fun invoke(
        keysetHandle: KeysetHandle,
        aead: Aead,
        associatedData: ByteArray
    ): String = TinkProtoKeysetFormat.serializeEncryptedKeyset(
        /* keysetHandle = */ keysetHandle,
        /* keysetEncryptionAead = */ aead,
        /* associatedData = */ associatedData
    ).let {
        encoder.encode(bytes = it)
    }

}