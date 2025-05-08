package io.gromif.astracrypt.auth.domain.usecase.auth.state

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.AuthState
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAuthStateFlowUseCaseTest {
    private lateinit var getAuthStateFlowUseCase: GetAuthStateFlowUseCase
    private val getAuthFlowUseCaseMock: GetAuthFlowUseCase = mockk()
    private val repositoryMock: Repository = mockk()

    @Before
    fun setUp() {
        getAuthStateFlowUseCase = GetAuthStateFlowUseCase(getAuthFlowUseCaseMock, repositoryMock)
    }

    @Test
    fun `should correctly return the AuthState when the auth is invalid`() = runTest {
        val targetConfig = Auth(type = AuthType.PASSWORD)
        val targetAuthState = false
        val targetSkinState = true
        val expected = AuthState(
            authType = targetConfig.type,
            authState = targetAuthState,
            skinType = targetConfig.skinType,
            skinState = targetSkinState
        )

        every { getAuthFlowUseCaseMock() } returns flowOf(targetConfig)
        every { repositoryMock.getAuthStateFlow() } returns flowOf(targetAuthState)
        every { repositoryMock.getSkinStateFlow() } returns flowOf(targetSkinState)

        val result = getAuthStateFlowUseCase().first()
        Assert.assertEquals(expected, result)

        verify(exactly = 1) { getAuthFlowUseCaseMock() }
        verify(exactly = 1) { repositoryMock.getAuthStateFlow() }
        verify(exactly = 1) { repositoryMock.getSkinStateFlow() }
    }

    @Test
    fun `should correctly return the AuthState when the skin auth is invalid`() = runTest {
        val targetConfig = Auth(skinType = SkinType.Calculator)
        val targetAuthState = true
        val targetSkinState = false
        val expected = AuthState(
            authType = targetConfig.type,
            authState = targetAuthState,
            skinType = targetConfig.skinType,
            skinState = targetSkinState
        )

        every { getAuthFlowUseCaseMock() } returns flowOf(targetConfig)
        every { repositoryMock.getAuthStateFlow() } returns flowOf(targetAuthState)
        every { repositoryMock.getSkinStateFlow() } returns flowOf(targetSkinState)

        val result = getAuthStateFlowUseCase().first()
        Assert.assertEquals(expected, result)

        verify(exactly = 1) { getAuthFlowUseCaseMock() }
        verify(exactly = 1) { repositoryMock.getAuthStateFlow() }
        verify(exactly = 1) { repositoryMock.getSkinStateFlow() }
    }

    @Test
    fun `should correctly return the AuthState when there's no auth`() = runTest {
        val targetConfig = Auth()
        val targetAuthState = true
        val targetSkinState = true
        val expected = AuthState(
            authType = targetConfig.type,
            authState = targetAuthState,
            skinType = targetConfig.skinType,
            skinState = targetSkinState
        )

        every { getAuthFlowUseCaseMock() } returns flowOf(targetConfig)
        every { repositoryMock.getAuthStateFlow() } returns flowOf(targetAuthState)
        every { repositoryMock.getSkinStateFlow() } returns flowOf(targetSkinState)

        val result = getAuthStateFlowUseCase().first()
        Assert.assertEquals(expected, result)

        verify(exactly = 1) { getAuthFlowUseCaseMock() }
        verify(exactly = 1) { repositoryMock.getAuthStateFlow() }
        verify(exactly = 1) { repositoryMock.getSkinStateFlow() }
    }

}