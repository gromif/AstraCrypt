package io.gromif.astracrypt.auth.domain.usecase.auth

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetAuthTypeUseCaseTest {
    private lateinit var setAuthTypeUseCase: SetAuthTypeUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()
    private val settingsRepository: SettingsRepository = mockk()
    private val tinkService: TinkService = mockk()

    @Before
    fun setUp() {
        setAuthTypeUseCase =
            SetAuthTypeUseCase(getAuthUseCase, setAuthUseCase, settingsRepository, tinkService)
    }

    @Test
    fun shouldGetCurrentAuth_and_computeAuthHash_thenUpdateRepositories() = runTest {
        val inputData = "random_data"
        val targetAuthType = AuthType.PASSWORD
        val currentAuth = Auth()
        val targetAuth = currentAuth.copy(type = targetAuthType)
        val targetHash = ByteArray(8)

        coEvery { getAuthUseCase() } returns currentAuth
        coEvery { tinkService.computeAuthHash(inputData) } returns targetHash
        coJustRun { settingsRepository.setAuthHash(targetHash) }
        coJustRun { setAuthUseCase(targetAuth) }

        setAuthTypeUseCase(targetAuthType, inputData)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { tinkService.computeAuthHash(inputData) }
        coVerify(exactly = 1) { settingsRepository.setAuthHash(targetHash) }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
    }
}
