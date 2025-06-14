package io.gromif.crypto.tink.core.encoders

import com.google.crypto.tink.subtle.Base64

class Base64Encoder : Encoder {

    override fun encode(bytes: ByteArray): String = Base64.encode(bytes)
    override fun decode(value: String): ByteArray = Base64.decode(value)

    override fun encode(value: String): String = encode(bytes = value.toByteArray())
    override fun decode(bytes: ByteArray): ByteArray = decode(value = bytes.decodeToString())

}