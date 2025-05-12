package io.gromif.crypto.tink.core.encoders

interface Encoder {

    fun encode(bytes: ByteArray): String
    fun decode(value: String): ByteArray

    fun encode(value: String): String
    fun decode(bytes: ByteArray): ByteArray

}