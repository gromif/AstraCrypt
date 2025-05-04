package io.gromif.secure_content.domain.usecase

import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.emptyFlow
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetContentModeFlowUseCaseTest {
    private lateinit var getContentModeFlowUseCase: GetContentModeFlowUseCase
    private val settingsRepositoryMock: SettingsRepository = mockk()

    @Before
    fun setUp() {
        getContentModeFlowUseCase = GetContentModeFlowUseCase(
            settingsRepository = settingsRepositoryMock
        )
    }

    @Test
    fun `should return the correct SecureContentMode flow`() {
        val targetSecureContentModeFlow = emptyFlow<SecureContentMode>()

        every {
            settingsRepositoryMock.getSecureContentModeFlow()
        } returns targetSecureContentModeFlow

        val result = getContentModeFlowUseCase()
        Assert.assertEquals(targetSecureContentModeFlow, result)

        verify(exactly = 1) { settingsRepositoryMock.getSecureContentModeFlow() }
    }

    @After
    fun tearDown() {
        confirmVerified(settingsRepositoryMock)
    }
}