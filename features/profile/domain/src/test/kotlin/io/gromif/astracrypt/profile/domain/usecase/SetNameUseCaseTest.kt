package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Profile
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetNameUseCaseTest {
    private lateinit var setNameUseCase: SetNameUseCase
    private val setProfileUseCaseMock: SetProfileUseCase = mockk()
    private val getProfileUseCaseMock: GetProfileUseCase = mockk()

    @Before
    fun setUp() {
        setNameUseCase = SetNameUseCase(setProfileUseCaseMock, getProfileUseCaseMock)
    }

    @Test
    fun shouldGetCurrentProfile_thenUpdateName() = runTest {
        val targetName = "User"
        val currentProfile = Profile()
        val targetProfile = currentProfile.copy(name = targetName)

        coEvery { getProfileUseCaseMock() } returns currentProfile
        coJustRun { setProfileUseCaseMock(targetProfile) }

        setNameUseCase(targetName)

        coVerify(exactly = 1) { getProfileUseCaseMock() }
        coVerify(exactly = 1) { setProfileUseCaseMock(targetProfile) }
    }
}