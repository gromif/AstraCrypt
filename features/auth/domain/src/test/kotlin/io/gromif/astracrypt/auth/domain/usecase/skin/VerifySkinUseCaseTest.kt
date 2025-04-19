package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VerifySkinUseCaseTest {
    private lateinit var verifySkinUseCase: VerifySkinUseCase
    private val settingsRepository: SettingsRepository = mockk()
    private val tinkService: TinkService = mockk()

    @Before
    fun setUp() {
        verifySkinUseCase = VerifySkinUseCase(settingsRepository, tinkService)
    }

    @Test
    fun shouldComputeSkinHash_and_compareToSaved_thenReturnResult() = runTest {
        val inputData = "random_data"
        val savedHash = ByteArray(2)
        val targetHash = ByteArray(8)

        coEvery { tinkService.computeSkinHash(inputData) } returns targetHash
        coEvery { settingsRepository.getSkinHash() } returns savedHash

        val compareResult = verifySkinUseCase(inputData)
        Assert.assertFalse(compareResult)

        coVerify(exactly = 1) { tinkService.computeSkinHash(inputData) }
        coVerify(exactly = 1) { settingsRepository.getSkinHash() }
    }

}