package io.gromif.secure_content.domain.usecase

import io.gromif.secure_content.domain.SecureContentMode
import io.gromif.secure_content.domain.SettingsRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SetContentModeUseCaseTest {
    private lateinit var setContentModeUseCase: SetContentModeUseCase
    private val settingsRepositoryMock: SettingsRepository = mockk()

    @Before
    fun setUp() {
        setContentModeUseCase = SetContentModeUseCase(
            settingsRepository = settingsRepositoryMock
        )
    }

    @Test
    fun `should properly update SecureContentMode preference`() = runTest {
        val targetMode = SecureContentMode.FORCE

        coJustRun { settingsRepositoryMock.setSecureContentMode(targetMode) }

        setContentModeUseCase(mode = targetMode)

        coVerify(exactly = 1) { settingsRepositoryMock.setSecureContentMode(targetMode) }
    }

    @After
    fun tearDown() {
        confirmVerified(settingsRepositoryMock)
    }
}