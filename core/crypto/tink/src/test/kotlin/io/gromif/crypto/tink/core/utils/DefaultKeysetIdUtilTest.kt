package io.gromif.crypto.tink.core.utils

import io.gromif.crypto.tink.core.encoders.Base64Encoder
import io.gromif.crypto.tink.core.hash.Sha256Util
import org.junit.Assert
import org.junit.Test

class DefaultKeysetIdUtilTest {
    private val defaultKeysetIdUtil = DefaultKeysetIdUtil(
        encoder = Base64Encoder(),
        hashUtil = Sha256Util()
    )

    @Test
    fun testCompute() {
        val result = defaultKeysetIdUtil.compute(
            tag = "test_tag",
            associatedData = "test_ad".toByteArray()
        )
        Assert.assertEquals("xGAiAG/sDM2eFdJinBV+FVCZTZ7xdfneniZ9Rjem5IY=", result)
    }

}