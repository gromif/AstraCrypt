package io.gromif.crypto.tink.keyset.parser

import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.keyset.KeysetAeadFactory

class KeysetParserWithKey(
    private val keysetParserWithAead: KeysetParserWithAead,
    private val keysetAeadFactory: KeysetAeadFactory
) {

    operator fun invoke(
        serializedKeyset: String,
        key: ByteArray,
        associatedData: ByteArray
    ): KeysetHandle = keysetParserWithAead(
        serializedKeyset = serializedKeyset,
        aead = keysetAeadFactory.invoke(key = key),
        associatedData = associatedData
    )

}