package com.nevidimka655.astracrypt.view.security.encryption

import androidx.fragment.app.Fragment

class DatabaseColumnsFragment : Fragment() {
    /*private val vm by activityViewModels<MainVM>()
    private val vmState by viewModels<DatabaseColumnsViewModel>()
    private val encryptionManager get() = vm.encryptionManager
    private val aeadInfo get() = encryptionManager.aeadInfo
    private var aeadInfoNew: EncryptionInfo
        get() = vmState.aeadInfoNew ?: aeadInfo
        set(value) {
            vmState.aeadInfoNew = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                PreferencesScreen {
                    PreferencesGroup {
                        val name = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.Name)) }
                        val thumb = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.Thumbnail)) }
                        val path = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.Path)) }
                        val details = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.Details)) }
                        val encryptionType = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.EncryptionType)) }
                        val thumbEncryptionType = rememberSaveable { mutableStateOf(isColumnNowSelected(DatabaseColumns.ThumbEncryptionType)) }

                        var dialogNameConfirmation by Dialogs.simple(
                            title = stringResource(id = R.string.dialog_encryptName),
                            text = stringResource(id = R.string.dialog_encryptName_msg)
                        ) {
                            name.value = true
                            addOrRemoveColumn(DatabaseColumns.Name, true)
                        }
                        CheckBoxOneLineListItem(
                            isChecked = name.value,
                            titleText = stringResource(id = R.string.name)
                        ) {
                            if (it) dialogNameConfirmation = true
                            else {
                                name.value = false
                                addOrRemoveColumn(DatabaseColumns.Name, false)
                            }
                        }
                        CheckBoxOneLineListItem(
                            isChecked = thumb.value,
                            titleText = stringResource(id = R.string.thumbnail)
                        ) {
                            thumb.value = it
                            addOrRemoveColumn(DatabaseColumns.Thumbnail, it)
                        }
                        CheckBoxOneLineListItem(
                            isChecked = path.value,
                            titleText = stringResource(id = R.string.path)
                        ) {
                            path.value = it
                            addOrRemoveColumn(DatabaseColumns.Path, it)
                        }
                        CheckBoxOneLineListItem(
                            isChecked = details.value,
                            titleText = stringResource(id = R.string.files_options_details)
                        ) {
                            details.value = it
                            addOrRemoveColumn(DatabaseColumns.Details, it)
                        }
                        CheckBoxOneLineListItem(
                            isChecked = encryptionType.value,
                            titleText = stringResource(id = R.string.encryption_type)
                        ) {
                            encryptionType.value = it
                            addOrRemoveColumn(DatabaseColumns.EncryptionType, it)
                        }
                        CheckBoxOneLineListItem(
                            isChecked = thumbEncryptionType.value,
                            titleText = stringResource(id = R.string.thumb_encryption_type)
                        ) {
                            thumbEncryptionType.value = it
                            addOrRemoveColumn(DatabaseColumns.ThumbEncryptionType, it)
                        }
                    }
                }
            }
        }
    }

    private fun addOrRemoveColumn(
        column: DatabaseColumns,
        state: Boolean,
    ) {
        aeadInfoNew = when (column) {
            DatabaseColumns.Details ->
                aeadInfoNew.copy(isFlagsEncrypted = state)

            DatabaseColumns.Name ->
                aeadInfoNew.copy(isNameEncrypted = state)

            DatabaseColumns.Thumbnail ->
                aeadInfoNew.copy(isThumbnailEncrypted = state)

            DatabaseColumns.Path ->
                aeadInfoNew.copy(isPathEncrypted = state)

            DatabaseColumns.EncryptionType ->
                aeadInfoNew.copy(isEncryptionTypeEncrypted = state)

            DatabaseColumns.ThumbEncryptionType ->
                aeadInfoNew.copy(isThumbEncryptionTypeEncrypted = state)

            else -> aeadInfoNew
        }
    }

    private fun isColumnNowSelected(column: DatabaseColumns) = when (column) {
        DatabaseColumns.Details ->
            aeadInfoNew.isFlagsEncrypted

        DatabaseColumns.Name ->
            aeadInfoNew.isNameEncrypted

        DatabaseColumns.Thumbnail ->
            aeadInfoNew.isThumbnailEncrypted

        DatabaseColumns.Path ->
            aeadInfoNew.isPathEncrypted

        DatabaseColumns.EncryptionType ->
            aeadInfoNew.isEncryptionTypeEncrypted

        DatabaseColumns.ThumbEncryptionType ->
            aeadInfoNew.isThumbEncryptionTypeEncrypted

        else -> false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireMenuHost().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.encryption_columns, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.sync_db -> {
                        if (aeadInfo.isDatabaseEncrypted && aeadInfo != aeadInfoNew) {
                            startDbTransformProcess()
                        } else saveEncryptionInfo()
                    }

                    else -> return false
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun startDbTransformProcess() {
        *//*WorkerFactory.startTransformDatabase(
            oldInfo = aeadInfo,
            newInfo = aeadInfoNew
        )*//*
        DatabaseTransformDialog().show(childFragmentManager, null)
        saveEncryptionInfo()
    }

    private fun saveEncryptionInfo() = with(encryptionManager) {
        *//*aeadInfo = aeadInfoNew
        save()*//*
    }*/

}