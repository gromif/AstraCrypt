package io.gromif.tinkLab.data.util

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializer
import io.gromif.tinkLab.domain.model.DataType
import io.gromif.tinkLab.domain.model.Key

class KeyGenerator(
    private val keysetSerializer: KeysetSerializer
) {

    operator fun invoke(dataType: DataType, aeadType: String): Key {
        val template = KeyTemplates.get(
            if (dataType == DataType.Files) "${aeadType}_1MB" else aeadType
        )
        val keysetHandle = KeysetHandle.generateNew(template)
        val serializedEncryptedKeyset = keysetSerializer(keysetHandle)
        return Key(
            dataType = dataType,
            rawKeyset = serializedEncryptedKeyset,
            aeadType = aeadType
        )
    }
}
