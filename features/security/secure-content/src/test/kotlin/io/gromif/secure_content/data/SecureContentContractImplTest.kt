package io.gromif.secure_content.data

import io.gromif.secure_content.data.mapper.toContractMode
import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SecureContentContractImplTest {
    private lateinit var secureContentContractImpl: SecureContentContractImpl
    private val settingsRepositoryMock: SettingsRepository = mockk()

    @Before
    fun setUp() {
        secureContentContractImpl = SecureContentContractImpl(
            settingsRepository = settingsRepositoryMock
        )
    }

    @Test
    fun `getContractModeFlow should return the correct flow`() = runTest {
        val targetMode = SecureContentMode.ENABLED
        val targetContractMode = targetMode.toContractMode()
        val targetSecureContentModeFlow = flowOf(targetMode)

        every {
            settingsRepositoryMock.getSecureContentModeFlow()
        } returns targetSecureContentModeFlow

        val result = secureContentContractImpl.getContractModeFlow().first()
        Assert.assertEquals(targetContractMode, result)

        verify(exactly = 1) { settingsRepositoryMock.getSecureContentModeFlow() }
    }

    @After
    fun tearDown() {
        confirmVerified(settingsRepositoryMock)
    }

}