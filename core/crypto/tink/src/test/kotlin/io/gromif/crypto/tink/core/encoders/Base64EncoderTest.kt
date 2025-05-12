package io.gromif.crypto.tink.core.encoders

import org.junit.Assert
import org.junit.Test

class Base64EncoderTest {
    private val encoder = Base64Encoder()

    @Test
    fun testEncode() {
        val result = encoder.encode("Hello World")
        Assert.assertEquals("SGVsbG8gV29ybGQ=", result)
    }

    @Test
    fun testDecode() {
        val result = encoder.decode("SGVsbG8gV29ybGQ=").decodeToString()
        Assert.assertEquals("Hello World", result)
    }

}