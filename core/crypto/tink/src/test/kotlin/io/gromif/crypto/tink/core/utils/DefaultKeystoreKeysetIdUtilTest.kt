package io.gromif.crypto.tink.core.utils

import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.core.hash.Sha256Util
import org.junit.Assert
import org.junit.Test

class DefaultKeystoreKeysetIdUtilTest {
    private val defaultKeystoreKeysetIdUtil = DefaultKeystoreKeysetIdUtil(
        encoder = Base64Encoder(),
        hashUtil = Sha256Util()
    )

    @Test
    fun testCompute() {
        val result = defaultKeystoreKeysetIdUtil.compute(
            tag = "test_tag",
            associatedData = "test_ad".toByteArray()
        )
        Assert.assertEquals("hdtA6X1x7aknjUqr+qgrTri0fhfnVTCldpEoqPlQYiw=", result)
    }

}