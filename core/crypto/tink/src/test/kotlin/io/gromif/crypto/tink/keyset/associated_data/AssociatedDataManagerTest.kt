package io.gromif.crypto.tink.keyset.associated_data

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.config.TinkConfig
import io.gromif.crypto.tink.core.extensions.prf
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class AssociatedDataManagerTest {
    private lateinit var tempFile: File
    private lateinit var fileSpy: File

    @Before
    fun setup() {
        TinkConfig.register()
        tempFile = File.createTempFile("associated_data", ".tmp")
        fileSpy = spyk(tempFile)
    }

    fun createManager() = AssociatedDataManager(associatedDataFile = fileSpy)

    @Test
    fun erase_shouldOverwriteData() {
        val associatedDataManager = createManager()

        runBlocking {
            val newData = associatedDataManager.getAssociatedData()

            associatedDataManager.erase()

            val randomData = fileSpy.readBytes()
            Assert.assertNotEquals(newData, randomData)
        }
    }

    @Test
    fun encryptWithPassword_shouldEncryptAndDecryptCorrectly() {
        every { fileSpy.exists() } returns false

        val associatedDataManager = createManager()

        runBlocking {
            val newData = associatedDataManager.getAssociatedData()

            val prf = KeysetHandle.generateNew(KeysetTemplates.PRF.HKDF_SHA256.params).prf()
            val password = "0000"
            associatedDataManager.encryptWithPassword(password, prf)

            val encryptedData = fileSpy.readBytes()
            Assert.assertNotEquals(newData, encryptedData)

            associatedDataManager.decryptWithPassword(password, prf)
            associatedDataManager.decrypt()
            val decryptedData = fileSpy.readBytes()

            Assert.assertArrayEquals(newData, decryptedData)
        }
    }

    @Test
    fun getAssociatedData_whenCalledTwice_shouldReuseCachedValue() {
        every { fileSpy.exists() } returns false

        val associatedDataManager = createManager()

        runBlocking {
            val a = associatedDataManager.getAssociatedData()
            val b = associatedDataManager.getAssociatedData()
            Assert.assertSame(a, b)
        }

        verify(exactly = 1) { fileSpy.exists() }
    }

    @Test
    fun getAssociatedData_whenCalledFirstTime_shouldCreateAndSaveData() {
        every { fileSpy.exists() } returns false

        every { fileSpy.createNewFile() } returns true

        val associatedDataManager = createManager()

        runBlocking {
            associatedDataManager.getAssociatedData()
            associatedDataManager.getAssociatedData()
        }

        verify(exactly = 1) { fileSpy.createNewFile() }

        Assert.assertNotEquals(0, tempFile.length())
    }

}