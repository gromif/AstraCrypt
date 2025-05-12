package io.gromif.crypto.tink.kms

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeystore
import io.gromif.crypto.tink.core.extensions.aead
import io.gromif.crypto.tink.keyset.KeysetIdUtil
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.crypto.tink.keyset.io.KeysetReader
import io.gromif.crypto.tink.keyset.io.KeysetWriter
import io.gromif.crypto.tink.keyset.parser.KeysetParserWithAead
import io.gromif.crypto.tink.keyset.serializers.KeysetSerializerWithAead
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

private const val tag = "test_keyset_tag"
private val associatedData = "test_keyset_associatedData".toByteArray()
private val params = KeysetTemplates.AEAD.XAES_256_GCM_160_BIT_NONCE_NO_PREFIX.params

class DefaultKeysetFactoryTest {
    private val keysetReaderMock: KeysetReader = mockk()
    private val keysetWriterMock: KeysetWriter = mockk()
    private val keysetSerializerWithAeadMock: KeysetSerializerWithAead = mockk()
    private val keysetParserWithAeadMock: KeysetParserWithAead = mockk()
    private val prefsKeysetIdUtilMock: KeysetIdUtil = mockk()
    private val masterKeysetIdUtilMock: KeysetIdUtil = mockk()

    private val defaultKeysetFactory = AndroidKeyManagementService(
        keysetReader = keysetReaderMock,
        keysetWriter = keysetWriterMock,
        keysetSerializerWithAead = keysetSerializerWithAeadMock,
        keysetParserWithAead = keysetParserWithAeadMock,
        prefsKeysetIdUtil = prefsKeysetIdUtilMock,
        masterKeysetIdUtil = masterKeysetIdUtilMock
    )

    @Before
    fun setup() {
        every { prefsKeysetIdUtilMock.compute(any(), any()) } returns "prefs_key"
        every { masterKeysetIdUtilMock.compute(any(), any()) } returns "master_key"

        TinkConfig.register()
    }

    @Test(expected = IllegalStateException::class)
    fun whenCreating_savedKeyset_withoutMasterKey_shouldThrowAnException() {
        coEvery { keysetReaderMock.read(any()) } returns "[keyset]"

        mockkStatic(AndroidKeystore::class) {
            every { AndroidKeystore.hasKey(any()) } returns false

            runBlocking {
                defaultKeysetFactory.create(tag, params, associatedData)
            }
        }
    }

    @Test
    fun whenCreating_withoutSavedKeyset_shouldWriteNewKeyset() {
        coEvery { keysetReaderMock.read(any()) } returns null

        coJustRun { keysetWriterMock.write(any(), any()) }

        mockkStatic(AndroidKeystore::class) {
            every { AndroidKeystore.hasKey(any()) } returns false
            justRun { AndroidKeystore.generateNewAes256GcmKey(any()) }
            every { AndroidKeystore.getAead(any()) } returns KeysetHandle.generateNew(params).aead()

            coEvery { keysetSerializerWithAeadMock(any(), any(), any()) } returns ""

            runBlocking {
                defaultKeysetFactory.create(tag, params, associatedData)
            }
        }

        coVerify { keysetSerializerWithAeadMock(any(), any(), any()) }
        coVerify { keysetWriterMock.write(any(), any()) }
    }

}