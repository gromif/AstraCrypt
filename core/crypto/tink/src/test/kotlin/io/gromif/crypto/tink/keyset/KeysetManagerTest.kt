package io.gromif.crypto.tink.keyset

import com.google.crypto.tink.KeysetHandle
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.kms.KeyManagementService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

private const val tag = "test_tag"
private val associatedData = "test_tag_ad".toByteArray()
private val params = KeysetTemplates.AEAD.AES128_GCM.params

class KeysetManagerTest {
    private val keyManagementServiceMock: KeyManagementService = mockk()
    private val associatedDataManagerMock: AssociatedDataManager = mockk()
    private val keysetManager = KeysetManager(
        keyManagementService = keyManagementServiceMock,
        associatedDataManager = associatedDataManagerMock
    )

    @Test
    fun getKeyset_NullAssociatedData_ShouldUseGlobalAssociatedData() {
        coEvery { associatedDataManagerMock.getAssociatedData() } returns ByteArray(0)

        coEvery {
            keyManagementServiceMock.create(any(), any(), any())
        } returns KeysetHandle.generateNew(params)

        runBlocking {
            keysetManager.getKeyset(tag, null, params)
            keysetManager.getKeyset(tag, associatedData, params)
        }

        coVerify(exactly = 1) { associatedDataManagerMock.getAssociatedData() }
    }

}