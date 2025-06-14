package io.gromif.crypto.tink.keyset.parser

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.TinkProtoKeysetFormat
import io.gromif.crypto.tink.core.encoders.Encoder

class KeysetParserWithAead(
    private val encoder: Encoder
) {

    operator fun invoke(
        serializedKeyset: String,
        aead: Aead,
        associatedData: ByteArray
    ): KeysetHandle {
        val encryptedKeyset = encoder.decode(value = serializedKeyset)
        return TinkProtoKeysetFormat.parseEncryptedKeyset(
            /* serializedEncryptedKeyset = */ encryptedKeyset,
            /* keysetEncryptionAead = */ aead,
            /* associatedData = */ associatedData
        )
    }

}