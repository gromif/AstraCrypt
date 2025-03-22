package io.gromif.astracrypt.profile.domain.usecase

import io.gromif.astracrypt.profile.domain.ValidationRules
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.repository.Repository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SetExternalAvatarUseCaseTest {
    private lateinit var setExternalAvatarUseCase: SetExternalAvatarUseCase
    private val setProfileUseCaseMock: SetProfileUseCase = mockk()
    private val getProfileUseCaseMock: GetProfileUseCase = mockk()
    private val getAvatarAeadUseCaseMock: GetAvatarAeadUseCase = mockk()
    private val repositoryMock: Repository = mockk()

    @Before
    fun setUp() {
        setExternalAvatarUseCase = SetExternalAvatarUseCase(
            setProfileUsecase = setProfileUseCaseMock,
            getProfileUsecase = getProfileUseCaseMock,
            getAvatarAeadUseCase = getAvatarAeadUseCaseMock,
            repository = repositoryMock
        )
    }

    @Test
    fun shouldGetCurrentAvatarAead_thenImportAvatarAndUpdateProfile() = runTest {
        val targetAvatarPath = "/test"
        val currentAvatarAead = -1
        val currentProfile = Profile()
        val targetProfile = currentProfile.copy(avatar = Avatar.External)

        coEvery { getAvatarAeadUseCaseMock() } returns currentAvatarAead
        coJustRun {
            repositoryMock.importAvatar(
                aead = currentAvatarAead,
                path = targetAvatarPath,
                quality = ValidationRules.QUALITY_ICON,
                size = ValidationRules.SIZE_ICON
            )
        }
        coEvery { getProfileUseCaseMock() } returns currentProfile
        coJustRun { setProfileUseCaseMock(targetProfile) }

        setExternalAvatarUseCase(targetAvatarPath)

        coVerify(exactly = 1) { getAvatarAeadUseCaseMock() }
        coVerify(exactly = 1) {
            repositoryMock.importAvatar(
                aead = currentAvatarAead,
                path = targetAvatarPath,
                quality = ValidationRules.QUALITY_ICON,
                size = ValidationRules.SIZE_ICON
            )
        }
        coVerify(exactly = 1) { getProfileUseCaseMock() }
        coVerify(exactly = 1) { setProfileUseCaseMock(targetProfile) }
    }
}