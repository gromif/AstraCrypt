package io.gromif.astracrypt.profile.presentation.settings

import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.profile.domain.model.ValidationRulesDto
import io.gromif.astracrypt.profile.domain.usecase.GetProfileFlowUseCase
import io.gromif.astracrypt.profile.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetExternalAvatarUseCase
import io.gromif.astracrypt.profile.domain.usecase.SetNameUseCase
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SettingsViewModelTest {
    private lateinit var viewModel: SettingsViewModel

    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val setNameUseCaseMock: SetNameUseCase = mockk()
    private val setAvatarUseCaseMock: SetAvatarUseCase = mockk()
    private val setExternalAvatarUseCaseMock: SetExternalAvatarUseCase = mockk()
    private val getValidationRulesUseCaseMock: GetValidationRulesUseCase = mockk()
    private val getProfileFlowUseCaseMock: GetProfileFlowUseCase = mockk()
    private val targetProfileFlow = flow<Profile> { Profile() }

    @Before
    fun setUp() {
        every { getValidationRulesUseCaseMock() } returns ValidationRulesDto(
            minNameLength = 0,
            maxNameLength = 0,
            iconSize = 0,
            iconQuality = 0
        )

        every { getProfileFlowUseCaseMock() } returns targetProfileFlow

        viewModel = SettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            setNameUsecase = setNameUseCaseMock,
            setAvatarUsecase = setAvatarUseCaseMock,
            setExternalAvatarUsecase = setExternalAvatarUseCaseMock,
            imageLoader = mockk(),
            getValidationRulesUsecase = getValidationRulesUseCaseMock,
            getProfileFlowUsecase = getProfileFlowUseCaseMock
        )
    }

    @Test
    fun `setName calls use case with correct parameters`() {
        val targetName = "User"

        coJustRun { setNameUseCaseMock(targetName) }

        viewModel.setName(targetName)

        coVerify(exactly = 1) { setNameUseCaseMock(targetName) }
    }

    @Test
    fun `setAvatar calls use case with correct parameters`() {
        val targetDefaultAvatar = DefaultAvatar.AVATAR_4

        coJustRun { setAvatarUseCaseMock(targetDefaultAvatar) }

        viewModel.setAvatar(targetDefaultAvatar)

        coVerify(exactly = 1) { setAvatarUseCaseMock(targetDefaultAvatar) }
    }

    @Test
    fun `setExternalAvatar calls use case with correct parameters`() {
        val targetAvatarPath = "path"

        coJustRun { setExternalAvatarUseCaseMock(targetAvatarPath) }

        viewModel.setExternalAvatar(targetAvatarPath)

        coVerify(exactly = 1) { setExternalAvatarUseCaseMock(targetAvatarPath) }
    }

}