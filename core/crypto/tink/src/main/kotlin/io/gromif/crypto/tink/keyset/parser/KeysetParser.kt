package io.gromif.crypto.tink.keyset.parser

import com.google.crypto.tink.InsecureSecretKeyAccess
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.TinkProtoKeysetFormat
import io.gromif.crypto.tink.core.encoders.Encoder

class KeysetParser(
    private val encoder: Encoder
) {

    operator fun invoke(
        serializedKeyset: String
    ): KeysetHandle {
        val encryptedKeyset = encoder.decode(value = serializedKeyset)
        return TinkProtoKeysetFormat.parseKeyset(
            /* serializedKeyset = */ encryptedKeyset,
            /* access = */ InsecureSecretKeyAccess.get()
        )
    }

}