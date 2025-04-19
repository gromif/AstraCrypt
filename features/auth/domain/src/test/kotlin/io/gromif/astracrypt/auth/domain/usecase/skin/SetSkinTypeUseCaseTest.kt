package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetSkinTypeUseCaseTest {
    private lateinit var setSkinTypeUseCase: SetSkinTypeUseCase
    private val getAuthUseCase: GetAuthUseCase = mockk()
    private val setAuthUseCase: SetAuthUseCase = mockk()
    private val settingsRepository: SettingsRepository = mockk()
    private val tinkService: TinkService = mockk()

    @Before
    fun setUp() {
        setSkinTypeUseCase =
            SetSkinTypeUseCase(getAuthUseCase, setAuthUseCase, settingsRepository, tinkService)
    }

    @Test
    fun shouldGetCurrentAuth_and_computeSkinHash_thenUpdateRepositories() = runTest {
        val inputData = "random_data"
        val targetSkinType = SkinType.Calculator
        val currentAuth = Auth()
        val targetAuth = currentAuth.copy(skinType = targetSkinType)
        val targetHash = ByteArray(8)

        coEvery { getAuthUseCase() } returns currentAuth
        coEvery { tinkService.computeSkinHash(inputData) } returns targetHash
        coJustRun { settingsRepository.setSkinHash(targetHash) }
        coJustRun { setAuthUseCase(targetAuth) }

        setSkinTypeUseCase(targetSkinType, inputData)

        coVerify(exactly = 1) { getAuthUseCase() }
        coVerify(exactly = 1) { tinkService.computeSkinHash(inputData) }
        coVerify(exactly = 1) { settingsRepository.setSkinHash(targetHash) }
        coVerify(exactly = 1) { setAuthUseCase(targetAuth) }
    }
}