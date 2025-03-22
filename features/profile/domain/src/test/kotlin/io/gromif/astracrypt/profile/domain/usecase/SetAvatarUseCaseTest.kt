package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.model.Profile
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetAvatarUseCaseTest {
    private lateinit var setAvatarUseCase: SetAvatarUseCase
    private val setProfileUseCaseMock: SetProfileUseCase = mockk()
    private val getProfileUseCaseMock: GetProfileUseCase = mockk()

    @Before
    fun setUp() {
        setAvatarUseCase = SetAvatarUseCase(setProfileUseCaseMock, getProfileUseCaseMock)
    }

    @Test
    fun shouldGetCurrentProfile_thenUpdateAvatar() = runTest {
        val targetAvatar = DefaultAvatar.AVATAR_4
        val currentProfile = Profile()
        val targetProfile = currentProfile.copy(avatar = Avatar.Default(targetAvatar))

        coEvery { getProfileUseCaseMock() } returns currentProfile
        coJustRun { setProfileUseCaseMock(targetProfile) }

        setAvatarUseCase(targetAvatar)

        coVerify(exactly = 1) { getProfileUseCaseMock() }
        coVerify(exactly = 1) { setProfileUseCaseMock(targetProfile) }
    }
}