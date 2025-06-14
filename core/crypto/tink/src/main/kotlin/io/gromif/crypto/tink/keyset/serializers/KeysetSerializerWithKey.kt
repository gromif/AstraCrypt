package io.gromif.crypto.tink.keyset.serializers

import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.keyset.KeysetAeadFactory

class KeysetSerializerWithKey(
    private val keysetSerializerWithAead: KeysetSerializerWithAead,
    private val keysetAeadFactory: KeysetAeadFactory
) {

    operator fun invoke(
        keysetHandle: KeysetHandle,
        key: ByteArray,
        associatedData: ByteArray
    ): String = keysetSerializerWithAead(
        keysetHandle = keysetHandle,
        aead = keysetAeadFactory.invoke(key = key),
        associatedData = associatedData
    )

}