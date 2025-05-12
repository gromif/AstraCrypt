package io.gromif.crypto.tink.core.hash

interface HashUtil {

    fun compute(value: ByteArray): ByteArray

}