package io.gromif.astracrypt.auth.domain.usecase.skin

import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class VerifySkinUseCaseTest {
    private lateinit var verifySkinUseCase: VerifySkinUseCase
    private val settingsRepositoryMock: SettingsRepository = mockk()
    private val repositoryMock: Repository = mockk()

    @Before
    fun setUp() {
        verifySkinUseCase = VerifySkinUseCase(settingsRepositoryMock, repositoryMock)
    }

    @Test
    fun shouldComputeSkinHash_and_compareToSaved_thenReturnResult() = runTest {
        val inputData = "random_data"
        val savedHash = ByteArray(2)

        coEvery { settingsRepositoryMock.getSkinHash() } returns savedHash
        coEvery { repositoryMock.verifySkin(savedHash, inputData) } returns true

        val compareResult = verifySkinUseCase(inputData)
        Assert.assertTrue(compareResult)

        coVerifySequence {
            settingsRepositoryMock.getSkinHash()
            repositoryMock.verifySkin(savedHash, inputData)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(settingsRepositoryMock, repositoryMock)
    }

}