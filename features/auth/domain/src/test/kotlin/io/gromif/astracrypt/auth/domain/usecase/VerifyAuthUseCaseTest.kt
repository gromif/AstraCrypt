package io.gromif.astracrypt.auth.domain.usecase

import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VerifyAuthUseCaseTest {
    private lateinit var verifyAuthUseCase: VerifyAuthUseCase
    private val settingsRepository: SettingsRepository = mockk()
    private val tinkService: TinkService = mockk()

    @Before
    fun setUp() {
        verifyAuthUseCase = VerifyAuthUseCase(settingsRepository, tinkService)
    }

    @Test
    fun shouldComputeAuthHash_and_compareToSaved_thenReturnResult() = runTest {
        val inputData = "random_data"
        val savedHash = ByteArray(2)
        val targetHash = ByteArray(8)

        coEvery { tinkService.computeAuthHash(inputData) } returns targetHash
        coEvery { settingsRepository.getAuthHash() } returns savedHash

        val compareResult = verifyAuthUseCase(inputData)
        Assert.assertFalse(compareResult)

        coVerify(exactly = 1) { tinkService.computeAuthHash(inputData) }
        coVerify(exactly = 1) { settingsRepository.getAuthHash() }
    }
}