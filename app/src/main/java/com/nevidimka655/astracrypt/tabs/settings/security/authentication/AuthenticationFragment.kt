package com.nevidimka655.astracrypt.tabs.settings.security.authentication

import androidx.fragment.app.Fragment

class AuthenticationFragment : Fragment() {
    /*private val vm by activityViewModels<MainVM>()
    private val authManager get() = vm.authManager
    private val encryptionManager get() = vm.encryptionManager

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                SettingsAuthenticationScreen()
            }
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun SettingsAuthenticationScreen() {
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val aeadInfo = encryptionManager.aeadInfo
        val info = authManager.info
        PreferencesScreen {
            val isAuthConfigured = remember(info) { info.authType != AuthType.NO_AUTH }
            PreferencesGroup(text = stringResource(id = R.string.settings_authentication)) {
                var dialogPasswordCheckDisableAuth by dialogCheckPassword {
                    disableAuthentication()
                    if (aeadInfo.isAssociatedDataEncrypted) {
                        setBindAssociatedData(
                            currentEncryptionInfo = aeadInfo,
                            newState = false,
                            passPhrase = it
                        )
                    }
                }
                var dialogPasswordSetup by dialogPassword {
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            with(authManager) {
                                val newAuthInfo = info.copy(authType = AuthType.PASSWORD)
                                saveInfo(newAuthInfo)
                                changePassword(it)
                            }
                        }
                    }
                }
                val authenticationMethods = remember {
                    listOf(
                        context.getString(R.string.withoutAuthentication),
                        context.getString(R.string.password)
                    )
                }
                var dialogAuthMethodsState by Dialogs.Selectable.radio(
                    onSelected = {
                        if (it != info.authType.ordinal) {
                            when (it) {
                                0 -> dialogPasswordCheckDisableAuth = true
                                1 -> dialogPasswordSetup = true
                            }
                        }
                    },
                    title = stringResource(id = R.string.settings_authentication),
                    items = authenticationMethods,
                    selectedItemIndex = info.authType.ordinal
                )
                Preference(
                    titleText = stringResource(id = R.string.settings_authenticationMethod),
                    summaryText = stringResource(id = info.authNameResId)
                ) {
                    dialogAuthMethodsState = true
                }
            }

            //
            PreferencesGroupAnimated(
                text = stringResource(id = R.string.hint),
                isVisible = isAuthConfigured
            ) {
                PreferencesSwitch(
                    titleText = stringResource(id = R.string.settings_showHint),
                    isChecked = info.hintIsEnabled
                ) {
                    scope.launch(Dispatchers.IO) { authManager.saveInfo(info.copy(hintIsEnabled = it)) }
                }
                AnimatedVisibility(visible = info.hintIsEnabled) {
                    val hint = remember(info) { info.hint ?: context.getString(R.string.none) }
                    var dialogHintEditor by dialogHintEditor(currentHint = hint) {
                        if (it != hint) scope.launch(Dispatchers.IO) {
                            authManager.saveInfo(info.copy(hint = it))
                        }
                    }
                    Preference(
                        titleText = stringResource(id = R.string.hint),
                        summaryText = hint
                    ) {
                        dialogHintEditor = true
                    }
                }
            }
            PreferencesGroupAnimated(
                text = stringResource(id = R.string.settings_encryption),
                isVisible = isAuthConfigured
            ) {
                var dialogPasswordCheckBindEncryption by dialogCheckPassword {
                    setBindAssociatedData(
                        currentEncryptionInfo = aeadInfo,
                        newState = !aeadInfo.isAssociatedDataEncrypted,
                        passPhrase = it
                    )
                }
                PreferencesSwitch(
                    titleText = stringResource(id = R.string.settings_bindWithFiles),
                    isChecked = aeadInfo.isAssociatedDataEncrypted
                ) {
                    dialogPasswordCheckBindEncryption = true
                }
            }
            PreferencesGroup(text = stringResource(id = R.string.settings_camouflage)) {
                val camouflageIndex = remember(info.camouflage) {
                    when (authManager.info.camouflage) {
                        is Camouflage.Calculator -> 1
                        Camouflage.None -> 0
                    }
                }

                var dialogCalculatorCombination by dialogCalculatorCombination {
                    scope.launch(Dispatchers.IO) {
                        val camouflage = Camouflage.Calculator().apply {
                            numberCombination = it
                        }
                        authManager.saveInfo(info.copy(camouflage = camouflage))
                        with(ApplicationComponentManager) {
                            setMainActivityState(false)
                            setCalculatorActivityState(true)
                        }
                    }
                }

                var dialogCamouflageMethodsState by dialogCamouflageMethods(
                    context = context,
                    selectedItemIndex = camouflageIndex
                ) {
                    if (it != camouflageIndex) {
                        when (it) {
                            0 -> lifecycleScope.launch(Dispatchers.IO) {
                                authManager.saveInfo(
                                    info.copy(camouflage = Camouflage.None)
                                )
                                with(ApplicationComponentManager) {
                                    setMainActivityState(true)
                                    setCalculatorActivityState(false)
                                }
                            }

                            1 -> dialogCalculatorCombination = true
                        }
                    }
                }
                Preference(
                    titleText = stringResource(id = R.string.settings_camouflage),
                    summaryText = stringResource(id = info.camouflageNameResId)
                ) {
                    dialogCamouflageMethodsState = true
                }
            }
        }
    }

    @Composable
    private fun dialogCamouflageMethods(
        context: Context, selectedItemIndex: Int, onSelected: (Int) -> Unit
    ): MutableState<Boolean> {
        val camouflageMethods = remember {
            listOf(
                context.getString(R.string.settings_camouflageType_no),
                context.getString(R.string.settings_camouflageType_calculator)
            )
        }
        return Dialogs.Selectable.radio(
            onSelected = onSelected,
            title = stringResource(id = R.string.settings_camouflage),
            items = camouflageMethods,
            selectedItemIndex = selectedItemIndex
        )
    }

    @Composable
    fun dialogCheckPassword(
        onMatch: (phrase: String) -> Unit,
        //onMismatch: (phrase: String) -> Unit
    ): MutableState<Boolean> {
        val context = LocalContext.current
        val state = dialogPassword {
            *//*if (authManager.checkPassword(it)) onMatch(it)
            else {
                Toast.makeText(
                    context, context.getString(R.string.t_invalidPass), Toast.LENGTH_SHORT
                ).show()
            }*//*
        }
        return state
    }

    @Composable
    private fun dialogPassword(onResult: (String) -> Unit) = Dialogs.TextFields.default(
        title = stringResource(id = R.string.password),
        params = Dialogs.TextFields.Params(
            label = stringResource(id = R.string.password),
            maxLength = AppConfig.AUTH_PASSWORD_MAX_LENGTH,
            singleLine = true
        ),
        onResult = onResult
    )

    @Composable
    private fun dialogHintEditor(
        currentHint: String,
        onResult: (String) -> Unit
    ) = Dialogs.TextFields.default(
        title = stringResource(id = R.string.hint),
        params = Dialogs.TextFields.Params(
            text = currentHint,
            label = stringResource(id = R.string.hint),
            selectAllText = true,
            maxLength = AppConfig.AUTH_HINT_MAX_LENGTH,
        ),
        onResult = onResult
    )

    @Composable
    private fun dialogCalculatorCombination(
        onResult: (String) -> Unit
    ) = Dialogs.TextFields.default(
        title = stringResource(id = R.string.settings_camouflage_numberCombination),
        params = Dialogs.TextFields.Params(
            label = stringResource(id = R.string.settings_camouflage_numberCombination),
            selectAllText = true,
            maxLength = CalculatorManager.MAX_NUM_LENGTH,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        ),
        onResult = onResult
    )

    private fun setBindAssociatedData(
        currentEncryptionInfo: EncryptionInfo,
        newState: Boolean,
        passPhrase: String
    ) {
        *//*if (newState) KeysetFactory.encryptAssociatedData(passPhrase)
        else KeysetFactory.decryptAssociatedData()*//*
        encryptionManager.aeadInfo = currentEncryptionInfo.copy(
            isAssociatedDataEncrypted = newState
        )
        encryptionManager.save()
    }

    private fun disableAuthentication() {
        authManager.saveInfo(authInfo = authManager.info.copy(authType = AuthType.NO_AUTH))
    }*/

}