package io.gromif.crypto.tink.core.encoders

import com.google.crypto.tink.subtle.Hex

class HexEncoder : Encoder {

    override fun encode(bytes: ByteArray): String {
        return Hex.encode(bytes)
    }

    override fun decode(value: String): ByteArray {
        return Hex.decode(value)
    }

    override fun encode(value: String): String {
        return encode(value.toByteArray())
    }

    override fun decode(bytes: ByteArray): ByteArray {
        return decode(bytes.decodeToString())
    }

}