package com.nevidimka655.tink_lab.data.util

import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.nevidimka655.crypto.tink.core.encoders.HexUtil
import com.nevidimka655.crypto.tink.core.hash.Sha256Util
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializer
import com.nevidimka655.crypto.tink.core.serializers.KeysetSerializerWithKey
import com.nevidimka655.tink_lab.domain.model.DataType
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.util.KeyGenerator

class KeyGeneratorImpl(
    private val keysetSerializer: KeysetSerializer,
    private val sha256Util: Sha256Util,
    private val hexUtil: HexUtil
): KeyGenerator {

    override fun invoke(dataType: DataType, aeadType: String): Key {
        val template = KeyTemplates.get(
            if (dataType == DataType.Files) "${aeadType}_1MB" else aeadType
        )
        val keysetHandle = KeysetHandle.generateNew(template)
        val serializedEncryptedKeyset = keysetSerializer(keysetHandle)
        val keysetHashArray = sha256Util.compute(
            value = serializedEncryptedKeyset.toByteArray()
        )
        val keysetHash = hexUtil.encode(bytes = keysetHashArray)
        return Key(
            dataType = dataType,
            rawKeyset = serializedEncryptedKeyset,
            aeadType = aeadType,
            hash = keysetHash
        )
    }

}