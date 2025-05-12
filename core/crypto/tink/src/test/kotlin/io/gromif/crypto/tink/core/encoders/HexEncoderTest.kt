package io.gromif.crypto.tink.core.encoders

import org.junit.Assert
import org.junit.Test

class HexEncoderTest {
    private val encoder = HexEncoder()

    @Test
    fun testEncode() {
        val result = encoder.encode("Test")
        Assert.assertEquals("54657374", result)
    }

    @Test
    fun testDecode() {
        val result = encoder.decode("54657374").decodeToString()
        Assert.assertEquals("Test", result)
    }

}