package io.gromif.crypto.tink.keyset.serializers

import com.google.crypto.tink.InsecureSecretKeyAccess
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.TinkProtoKeysetFormat
import io.gromif.crypto.tink.core.encoders.Encoder

class KeysetSerializer(
    private val encoder: Encoder
) {

    operator fun invoke(item: KeysetHandle): String {
        return TinkProtoKeysetFormat.serializeKeyset(
            /* keysetHandle = */ item,
            /* access = */ InsecureSecretKeyAccess.get()
        ).let {
            encoder.encode(bytes = it)
        }
    }

}