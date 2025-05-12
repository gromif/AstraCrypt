package io.gromif.crypto.tink.keyset

import io.gromif.crypto.tink.core.hash.HashUtil

class KeysetKeyFactory(
    private val hashUtil: HashUtil
) {

    fun invoke(key: ByteArray) = hashUtil.compute(value = key).copyOf(32)

}