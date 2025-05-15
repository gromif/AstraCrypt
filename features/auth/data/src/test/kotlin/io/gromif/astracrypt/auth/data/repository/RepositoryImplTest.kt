package io.gromif.astracrypt.auth.data.repository

import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {
    private lateinit var repositoryImpl: RepositoryImpl
    private val tinkServiceMock: TinkService = mockk()

    @Before
    fun setUp() {
        repositoryImpl = RepositoryImpl(tinkServiceMock)
    }

    @Test
    fun `verifyTimeout correctly invalidates auth states when the timeout is valid`() = runTest {
        val targetTimeout = Timeout.SECONDS_10
        val targetCurrentTime = System.currentTimeMillis()
        val targetLastActiveTimeMillis = targetCurrentTime - Timeout.SECONDS_5.seconds * 1000
        val expected = false

        repositoryImpl.verifyTimeout(
            currentTime = targetCurrentTime,
            lastActiveTime = targetLastActiveTimeMillis,
            timeout = targetTimeout
        )
        val authState = repositoryImpl.getAuthStateFlow().first()
        val skinState = repositoryImpl.getSkinStateFlow().first()
        val result = authState && skinState
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `verifyTimeout correctly invalidates auth states when the timeout is invalid`() = runTest {
        val targetTimeout = Timeout.SECONDS_10
        val targetCurrentTime = System.currentTimeMillis()
        val targetLastActiveTimeMillis = targetCurrentTime - Timeout.SECONDS_15.seconds * 1000
        val expected = false

        repositoryImpl.verifyTimeout(
            currentTime = targetCurrentTime,
            lastActiveTime = targetLastActiveTimeMillis,
            timeout = targetTimeout
        )
        val authState = repositoryImpl.getAuthStateFlow().first()
        val skinState = repositoryImpl.getSkinStateFlow().first()
        val result = authState && skinState
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `verifyTimeout doesn't invalidate auth states when the timeout is disabled`() = runTest {
        val targetTimeout = Timeout.NEVER
        val expected = false

        repositoryImpl.verifyTimeout(
            currentTime = 0,
            lastActiveTime = 0,
            timeout = targetTimeout
        )
        val authState = repositoryImpl.getAuthStateFlow().first()
        val skinState = repositoryImpl.getSkinStateFlow().first()
        val result = authState && skinState
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `verifyAuth correctly verifies the hash, updates the auth state, and returns the result`() = runTest {
        val targetSecret = "secret"
        val targetSavedHash = targetSecret.toByteArray()
        val targetComputeHash = targetSecret.toByteArray()
        val expected = true

        coEvery { tinkServiceMock.computeAuthHash(targetSecret) } returns targetComputeHash

        val result = repositoryImpl.verifyAuth(
            savedHash = targetSavedHash,
            secret = targetSecret
        )
        val stateResult = repositoryImpl.getAuthStateFlow().first()
        Assert.assertEquals(expected, result)
        Assert.assertEquals(expected, stateResult)

        coVerify(exactly = 1) { tinkServiceMock.computeAuthHash(targetSecret) }
    }

    @Test
    fun `verifySkin correctly verifies the hash, updates the skin state, and returns the result`() = runTest {
        val targetSecret = "secret"
        val targetSavedHash = "secret2".toByteArray()
        val targetComputeHash = targetSecret.toByteArray()
        val expected = false

        coEvery { tinkServiceMock.computeSkinHash(targetSecret) } returns targetComputeHash

        val result = repositoryImpl.verifySkin(
            savedHash = targetSavedHash,
            secret = targetSecret
        )
        val stateResult = repositoryImpl.getSkinStateFlow().first()
        Assert.assertEquals(expected, result)
        Assert.assertEquals(expected, stateResult)

        coVerify(exactly = 1) { tinkServiceMock.computeSkinHash(targetSecret) }
    }

    @After
    fun tearDown() {
        confirmVerified(tinkServiceMock)
    }
}
