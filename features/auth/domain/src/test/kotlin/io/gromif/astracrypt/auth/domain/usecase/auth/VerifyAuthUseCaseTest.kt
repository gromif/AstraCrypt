package io.gromif.astracrypt.auth.domain.usecase.auth

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

class VerifyAuthUseCaseTest {
    private lateinit var verifyAuthUseCase: VerifyAuthUseCase
    private val settingsRepositoryMock: SettingsRepository = mockk()
    private val repositoryMock: Repository = mockk()

    @Before
    fun setUp() {
        verifyAuthUseCase = VerifyAuthUseCase(settingsRepositoryMock, repositoryMock)
    }

    @Test
    fun `should call verifyAuth with correct parameters and return the result`() = runTest {
        val inputData = "random_data"
        val savedHash = ByteArray(2)

        coEvery { settingsRepositoryMock.getAuthHash() } returns savedHash
        coEvery { repositoryMock.verifyAuth(savedHash, inputData) } returns true

        val compareResult = verifyAuthUseCase(inputData)
        Assert.assertTrue(compareResult)

        coVerifySequence {
            settingsRepositoryMock.getAuthHash()
            repositoryMock.verifyAuth(savedHash, inputData)
        }
    }

    @After
    fun tearDown() {
        confirmVerified(settingsRepositoryMock, repositoryMock)
    }
}
