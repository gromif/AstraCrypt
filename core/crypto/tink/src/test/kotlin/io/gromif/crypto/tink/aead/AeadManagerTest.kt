package io.gromif.crypto.tink.aead

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters
import com.google.crypto.tink.config.TinkConfig
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class AeadManagerTest {
    lateinit var mock: KeysetManager
    lateinit var aeadManager: AeadManager

    @Before
    fun setup() {
        TinkConfig.register()

        mock = mockk<KeysetManager>()
        aeadManager = AeadManager(mock)
    }

    private fun mockKeysetFactory(parameters: Parameters) {
        // mock to intercept the keyset factory
        coEvery {
            mock.getKeyset(
                tag = any(),
                associatedData = any(),
                keyParams = any(),
                keysetTag = any(),
                keysetTagHash = any(),
                cache = any()
            )
        } returns KeysetHandle.generateNew(parameters)
    }

    private fun verifyKeysetFactory(parameters: Parameters) {
        // any keyset should be retrieved only once
        coVerify(exactly = 1) {
            mock.getKeyset(
                tag = any(),
                associatedData = any(),
                keyParams = any(),
                keysetTag = any(),
                keysetTagHash = any(),
                cache = any()
            )
        }
    }

    @Test
    fun aead_whenCalledTwice_shouldReuseCachedValue() {
        val tag = "test_tag"
        val params = KeysetTemplates.AEAD.XAES_256_GCM_160_BIT_NONCE_NO_PREFIX.params
        mockKeysetFactory(parameters = params)

        // testing the mechanism of caching keysets
        runBlocking {
            aeadManager.aead(tag = tag, keyParams = params)
            aeadManager.aead(tag = tag, keyParams = params)
        }
        verifyKeysetFactory(parameters = params)
    }

    @Test
    fun prf_whenCalledTwice_shouldReuseCachedValue() {
        val tag = "test_tag"
        val params = KeysetTemplates.PRF.HMAC_SHA512_PRF.params
        mockKeysetFactory(parameters = params)

        // testing the mechanism of caching keysets
        runBlocking {
            aeadManager.prf(tag = tag, keyParams = params)
            aeadManager.prf(tag = tag, keyParams = params)
        }
        verifyKeysetFactory(parameters = params)
    }

    @Test
    fun deterministicAead_whenCalledTwice_shouldReuseCachedValue() {
        val tag = "test_tag"
        val params = KeysetTemplates.DeterministicAEAD.AES256_SIV.params
        mockKeysetFactory(parameters = params)

        // testing the mechanism of caching keysets
        runBlocking {
            aeadManager.deterministicAead(tag = tag, keyParams = params)
            aeadManager.deterministicAead(tag = tag, keyParams = params)
        }
        verifyKeysetFactory(parameters = params)
    }

    @Test
    fun streamingAead_whenCalledTwice_shouldReuseCachedValue() {
        val tag = "test_tag"
        val params = KeysetTemplates.Stream.AES256_GCM_HKDF_1MB.params
        mockKeysetFactory(parameters = params)

        // testing the mechanism of caching keysets
        runBlocking {
            aeadManager.streamingAead(tag = tag, keyParams = params)
            aeadManager.streamingAead(tag = tag, keyParams = params)
        }
        verifyKeysetFactory(parameters = params)
    }

    @After
    fun cleanup() {
        confirmVerified(mock)
    }

}