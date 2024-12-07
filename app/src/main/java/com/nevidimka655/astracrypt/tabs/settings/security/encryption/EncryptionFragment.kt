package com.nevidimka655.astracrypt.tabs.settings.security.encryption

import androidx.fragment.app.Fragment

class EncryptionFragment : Fragment() {
   /* private val vm by activityViewModels<MainVM>()
    private val encryptionManager get() = vm.encryptionManager
    private val aeadInfoNew get() = encryptionManager.aeadInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireMenuHost().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.encryption, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.help -> findNavController().navigate(
                        R.id.action_global_helpFragment, args = bundleOf(
                            Pair(
                                HelpFragment.ARGS_HELP_INDEX_NAME,
                                HelpIndex.SETTINGS_ENCRYPTION.ordinal
                            )
                        )
                    )

                    else -> return false
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NewApi")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                SettingsEncryptionScreen()
            }
        }
    }

    private fun addNoEncryptionOption(it: MutableList<String>) {
        it.add(0, getString(R.string.withoutEncryption))
    }

    @SuppressLint("NewApi")
    @Composable
    fun SettingsEncryptionScreen() {
        val scope = rememberCoroutineScope()
        val itemsMajor = remember {
            AlgorithmPaywallListFactory.fetchItemsMajor().also {
                addNoEncryptionOption(it)
            }
        }
        val itemsMinor = remember {
            AlgorithmPaywallListFactory.fetchItemsMinor().also {
                addNoEncryptionOption(it)
            }
        }
        val itemsAead = remember {
            AlgorithmPaywallListFactory.fetchItemsAead().also {
                addNoEncryptionOption(it)
            }
        }
        val aeadInfo = encryptionManager.aeadInfo
        PreferencesScreen {
            PreferencesGroup(text = stringResource(id = R.string.files)) {
                val filesEncryption = remember(aeadInfo) {
                    encryptionManager.getFileEncryptionName()
                        ?: getString(R.string.withoutEncryption)
                }
                var dialogFilesState by Dialogs.Selectable.radio(
                    onSelected = { onFilesEncryptionSelected(which = it) },
                    title = stringResource(id = R.string.files),
                    items = itemsMajor,
                    selectedItemIndex = with(aeadInfo.fileEncryptionOrdinal) {
                        if (this != -1) this + 1 else 0
                    }
                )
                var dialogThumbsState by Dialogs.Selectable.radio(
                    onSelected = { onThumbEncryptionSelected(which = it) },
                    title = stringResource(id = R.string.thumbnail),
                    items = itemsMinor,
                    selectedItemIndex = with(aeadInfo.thumbEncryptionOrdinal) {
                        if (this != -1) this - 3 else 0
                    }
                )
                Preference(
                    titleText = stringResource(id = R.string.files),
                    summaryText = filesEncryption
                ) {
                    dialogFilesState = true
                }
                val thumbEncryption = remember(aeadInfo) {
                    encryptionManager.getThumbEncryptionName()
                        ?: getString(R.string.withoutEncryption)
                }
                Preference(
                    titleText = stringResource(id = R.string.thumbnail),
                    summaryText = thumbEncryption
                ) {
                    dialogThumbsState = true
                }
            }
            PreferencesGroup(text = stringResource(id = R.string.settings_database)) {
                val dbEncryption = remember(aeadInfo) {
                    encryptionManager.getDbEncryptionName() ?: getString(R.string.withoutEncryption)
                }
                var selectedDbEncryptionToConfirm by rememberSaveable { mutableIntStateOf(-1) }
                var dbEncryptConfirmation by Dialogs.simple(
                    title = stringResource(id = R.string.dialog_applyNewSettings),
                    text = stringResource(id = R.string.dialog_applyNewSettings_message)
                ) {
                    scope.launch {
                        onDatabaseEncryptionSelected(which = selectedDbEncryptionToConfirm)
                    }
                }
                var selectedNotesDbEncryptionToConfirm by rememberSaveable { mutableIntStateOf(-1) }
                var notesDbEncryptConfirmation by Dialogs.simple(
                    title = stringResource(id = R.string.dialog_applyNewSettings),
                    text = stringResource(id = R.string.dialog_applyNewSettings_message)
                ) {
                    scope.launch {
                        onNotesDatabaseEncryptionSelected(which = selectedNotesDbEncryptionToConfirm)
                    }
                }

                var dialogDatabaseState by Dialogs.Selectable.radio(
                    onSelected = {
                        selectedDbEncryptionToConfirm = it
                        dbEncryptConfirmation = true
                    },
                    title = stringResource(id = R.string.settings_database),
                    items = itemsAead,
                    selectedItemIndex = run {
                        val name = encryptionManager.getDbEncryptionName()
                        if (name != null) KeysetTemplates.AEAD.valueOf(name).ordinal + 1
                        else 0
                    }
                )
                var dialogNotesDatabaseState by Dialogs.Selectable.radio(
                    onSelected = {
                        selectedNotesDbEncryptionToConfirm = it
                        notesDbEncryptConfirmation = true
                    },
                    title = stringResource(id = R.string.notes),
                    items = itemsAead,
                    selectedItemIndex = run {
                        val keysetType = aeadInfoNew.notesEncryptionOrdinal
                        if (keysetType != -1) keysetType + 1 else 0
                    }
                )
                Preference(
                    titleText = stringResource(id = R.string.settings_database),
                    summaryText = dbEncryption
                ) {
                    dialogDatabaseState = true
                }
                val columnsEncrypted = remember(aeadInfo) {
                    val prefixText = getString(R.string.settings_columns_summary)
                    val suffix = with(aeadInfo) {
                        val str = StringBuilder()
                        fun append(value: String) = str.append("$value, ")
                        if (isNameEncrypted) append(getString(R.string.name))
                        if (isThumbnailEncrypted) append(getString(R.string.thumbnail))
                        if (isPathEncrypted) append(getString(R.string.path))
                        if (isFlagsEncrypted) append(getString(R.string.files_options_details))
                        if (isEncryptionTypeEncrypted) append(getString(R.string.encryption_type))
                        if (isThumbEncryptionTypeEncrypted) append(getString(R.string.thumb_encryption_type))
                        str.removeSuffix(", ")
                    }.toString().lowercase()
                    "$prefixText: $suffix"
                }
                Preference(
                    titleText = stringResource(id = R.string.settings_columns),
                    summaryText = columnsEncrypted
                ) {
                    findNavController().navigate(R.id.action_encryptionFragment_to_databaseColumnsFragment)
                }
                val notesEncryption = remember(aeadInfo) {
                    encryptionManager.getNotesEncryptionName()
                        ?: getString(R.string.withoutEncryption)
                }
                Preference(
                    titleText = stringResource(id = R.string.notes),
                    summaryText = notesEncryption
                ) {
                    dialogNotesDatabaseState = true
                }
            }
            PreferencesGroup(text = stringResource(id = R.string.settings)) {
                var settingsEncryption by remember(aeadInfo) {
                    //mutableIntStateOf(masterSettings.getInt(PrefsKeys.ENCRYPTION_SETTINGS, -1))
                    mutableIntStateOf(-1)
                }
                val settingsEncryptionName = remember(settingsEncryption) {
                    if (settingsEncryption == -1) getString(R.string.withoutEncryption)
                    else KeysetTemplates.AEAD.entries[settingsEncryption].name
                }
                var dialogSettingsState by Dialogs.Selectable.radio(
                    onSelected = {
                        settingsEncryption = if (it == 0) -1 else it - 1
                        scope.launch { onSettingsEncryptionSelected(which = it) }
                    },
                    title = stringResource(id = R.string.settings),
                    items = itemsAead,
                    selectedItemIndex = with(settingsEncryption) {
                        if (this == -1) 0 else this + 1
                    }
                )
                Preference(
                    titleText = stringResource(id = R.string.settings),
                    summaryText = settingsEncryptionName
                ) {
                    dialogSettingsState = true
                }
            }
        }
    }

    private fun onFilesEncryptionSelected(which: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            with(vm.encryptionManager) {
                val fileEncryptionOrdinal = if (which > 0) {
                    val streamingType = KeysetTemplates.Stream.entries[which - 1]
                    *//*with(KeysetFactory) {
                        stream(requireContext(), streamingType)
                        saveKeystoreFile()
                    }*//*
                    streamingType.ordinal
                } else -1
                aeadInfo =
                    aeadInfoNew.copy(fileEncryptionOrdinal = fileEncryptionOrdinal)
                save()
            }
        }
    }

    private suspend fun onNotesDatabaseEncryptionSelected(which: Int) {
        val notesEncryptionOrdinal = if (which == 0) -1 else which - 1
        val oldEncryptionInfo = aeadInfoNew.copy()
        if (oldEncryptionInfo.notesEncryptionOrdinal != notesEncryptionOrdinal) {
            val newEncryptionInfo = oldEncryptionInfo
                .copy(notesEncryptionOrdinal = notesEncryptionOrdinal)
            encryptionManager.aeadInfo = newEncryptionInfo
            *//*WorkerFactory.startTransformNotes(
                oldInfo = oldEncryptionInfo,
                newInfo = newEncryptionInfo
            )*//*
            DatabaseTransformDialog().show(childFragmentManager, null)
            withContext(Dispatchers.IO) { encryptionManager.save() }
        }
    }

    private fun onThumbEncryptionSelected(which: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            with(encryptionManager) {
                val thumbEncryptionOrdinal = if (which > 0) {
                    val streamingType = KeysetTemplates.Stream.entries[which + 3]
//                    with(KeysetFactory) {
//                        stream(requireContext(), streamingType)
//                        saveKeystoreFile()
//                    }
                    streamingType.ordinal
                } else -1
                aeadInfo = aeadInfoNew
                    .copy(thumbEncryptionOrdinal = thumbEncryptionOrdinal)
                vm.saveProfileInfo(
                    profileInfo = vm.profileInfoFlow.value,
                    force = true
                )
                save()
            }
        }
    }

    private suspend fun onSettingsEncryptionSelected(which: Int) {
        *//*withContext(Dispatchers.IO) {
            masterSettings.edit(true) {
                putInt(PrefsKeys.ENCRYPTION_SETTINGS, if (which == 0) -1 else which - 1)
            }
            settings.transform(
                context = requireContext(),
                aead = KeysetTemplates.AEAD.entries[if (which == 0) 0 else which - 1],
                isEncryptionEnabledNewState = which > 0
            )
        }*//*
    }

    private suspend fun onDatabaseEncryptionSelected(which: Int) {
        val databaseEncryptionOrdinal = if (which > 0) {
            KeysetTemplates.AEAD.entries[which - 1].ordinal
        } else -1
        val oldEncryptionInfo = aeadInfoNew.copy()
        val newEncryptionInfo = oldEncryptionInfo
            .copy(databaseEncryptionOrdinal = databaseEncryptionOrdinal)
        encryptionManager.aeadInfo = newEncryptionInfo
        with(encryptionManager.aeadInfo) {
            if (isEncryptionTypeEncrypted || isNameEncrypted ||
                isFlagsEncrypted || isPathEncrypted ||
                isThumbEncryptionTypeEncrypted || isThumbnailEncrypted
            ) {
                *//*WorkerFactory.startTransformDatabase(
                    oldInfo = oldEncryptionInfo,
                    newInfo = newEncryptionInfo
                )*//*
                DatabaseTransformDialog().show(childFragmentManager, null)
            }
        }
        withContext(Dispatchers.IO) { encryptionManager.save() }
    }*/

}