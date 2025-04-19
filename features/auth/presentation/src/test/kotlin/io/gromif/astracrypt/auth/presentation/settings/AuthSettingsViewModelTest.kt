package io.gromif.astracrypt.auth.presentation.settings

import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.encryption.SetBindTinkAdUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintTextUseCase
import io.gromif.astracrypt.auth.domain.usecase.hint.SetHintVisibilityUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.SetTimeoutUseCase
import io.gromif.astracrypt.utils.app.AppComponentService
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthSettingsViewModelTest {
    private lateinit var authSettingsViewModel: AuthSettingsViewModel
    private val defaultDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val appComponentServiceMock: AppComponentService = mockk()
    private val setSkinTypeUseCaseMock: SetSkinTypeUseCase = mockk()
    private val setAuthTypeUseCaseMock: SetAuthTypeUseCase = mockk()
    private val verifyAuthUseCaseMock: VerifyAuthUseCase = mockk()
    private val setHintVisibilityUseCaseMock: SetHintVisibilityUseCase = mockk()
    private val setHintTextUseCaseMock: SetHintTextUseCase = mockk()
    private val setBindTinkAdUseCaseMock: SetBindTinkAdUseCase = mockk()
    private val setTimeoutUseCaseMock: SetTimeoutUseCase = mockk()
    private val getAuthFlowUseCaseMock: GetAuthFlowUseCase = mockk()
    private val targetAuthFlow = flow<Auth> { Auth() }

    @Before
    fun setUp() {
        every { getAuthFlowUseCaseMock() } returns targetAuthFlow

        authSettingsViewModel = AuthSettingsViewModel(
            defaultDispatcher = defaultDispatcher,
            appComponentService = appComponentServiceMock,
            setSkinTypeUseCase = setSkinTypeUseCaseMock,
            setAuthTypeUseCase = setAuthTypeUseCaseMock,
            verifyAuthUseCase = verifyAuthUseCaseMock,
            setHintVisibilityUseCase = setHintVisibilityUseCaseMock,
            setHintTextUseCase = setHintTextUseCaseMock,
            setBindTinkAdUseCase = setBindTinkAdUseCaseMock,
            setTimeoutUseCase = setTimeoutUseCaseMock,
            getAuthFlowUseCase = getAuthFlowUseCaseMock
        )
    }

    @Test
    fun `should start listening for auth changes`() {
        verify(exactly = 1) { getAuthFlowUseCaseMock() }
    }

    @Test
    fun `should update auth settings when disabling it completely`() {
        coJustRun { setAuthTypeUseCaseMock(authType = null, data = null) }

        authSettingsViewModel.disable()

        coVerify(exactly = 1) {
            setAuthTypeUseCaseMock(authType = null, data = null)
        }
    }

    @Test
    fun `should update skin settings and enable the main application activity when disabling the skin completely`() {
        coJustRun { setSkinTypeUseCaseMock(skinType = null, data = null) }
        justRun { appComponentServiceMock.main = true }
        justRun { appComponentServiceMock.calculator = false }

        authSettingsViewModel.disableSkin()

        coVerifyOrder {
            setSkinTypeUseCaseMock(skinType = null, data = null)
            with(appComponentServiceMock) {
                main = true
                calculator = false
            }
        }
    }

    @Test
    fun `should update skin settings and enable the calculator activity when enabling the calculator skin`() {
        val targetCombination = "5891"

        coJustRun { setSkinTypeUseCaseMock(skinType = SkinType.Calculator, data = targetCombination) }
        justRun { appComponentServiceMock.main = false }
        justRun { appComponentServiceMock.calculator = true }

        authSettingsViewModel.setCalculatorSkin(targetCombination)

        coVerifyOrder {
            setSkinTypeUseCaseMock(skinType = SkinType.Calculator, data = targetCombination)
            with(appComponentServiceMock) {
                calculator = true
                main = false
            }
        }
    }

    @Test
    fun `should call setBindTinkAdUseCase when updating bindTinkAd state`() {
        val targetState = true
        val targetPassword = "5891"

        coJustRun { setBindTinkAdUseCaseMock(bind = targetState, password = targetPassword) }

        authSettingsViewModel.setBindAssociatedData(targetState, targetPassword)

        coVerify(exactly = 1) {
            setBindTinkAdUseCaseMock(bind = targetState, password = targetPassword)
        }
    }

    @Test
    fun `should call setTimeoutUseCaseMock when updating timeout state`() {
        val targetTimeout = Timeout.SECONDS_60

        coJustRun { setTimeoutUseCaseMock(targetTimeout) }

        authSettingsViewModel.setTimeout(targetTimeout)

        coVerify(exactly = 1) {
            setTimeoutUseCaseMock(targetTimeout)
        }
    }

    @Test
    fun `should update the auth type when setting the password`() {
        val targetAuthType = AuthType.PASSWORD
        val targetPassword = "12345"

        coJustRun { setAuthTypeUseCaseMock(authType = targetAuthType, data = targetPassword) }

        authSettingsViewModel.setPassword(targetPassword)

        coVerify(exactly = 1) {
            setAuthTypeUseCaseMock(authType = targetAuthType, data = targetPassword)
        }
    }

    @Test
    fun `should call verifyAuthUseCase when verifying the password`() = runTest {
        val targetPassword = "12345"

        coJustRun { verifyAuthUseCaseMock(password = targetPassword) }

        authSettingsViewModel.verifyPassword(targetPassword)

        coVerify(exactly = 1) {
            verifyAuthUseCaseMock(password = targetPassword)
        }
    }

    @Test
    fun `should call setHintVisibilityUseCase when updating the hint state`() {
        val targetState = true

        coJustRun { setHintVisibilityUseCaseMock(visible = targetState) }

        authSettingsViewModel.setHintState(targetState)

        coVerify(exactly = 1) {
            setHintVisibilityUseCaseMock(visible = targetState)
        }
    }

    @Test
    fun `should call setHintTextUseCase when updating the hint text`() {
        val targetText = "hint"

        coJustRun { setHintTextUseCaseMock(text = targetText) }

        authSettingsViewModel.setHintText(targetText)

        coVerify(exactly = 1) {
            setHintTextUseCaseMock(text = targetText)
        }
    }

}