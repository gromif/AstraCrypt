package com.nevidimka655.astracrypt.tabs.settings.security.encryption

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.entities.EncryptionInfo
import com.nevidimka655.astracrypt.ui.dialogs.DatabaseTransformDialog
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.enums.DatabaseColumns
import com.nevidimka655.astracrypt.utils.extensions.requireMenuHost
import com.nevidimka655.astracrypt.work.utils.WorkerFactory
import com.nevidimka655.ui.compose_core.CheckBoxOneLineListItem
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.dialogs.Dialogs

class DatabaseColumnsFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()
    private val vmState by viewModels<DatabaseColumnsViewModel>()
    private val encryptionManager get() = vm.encryptionManager
    private val encryptionInfo get() = encryptionManager.encryptionInfo
    private var encryptionInfoNew: EncryptionInfo
        get() = vmState.encryptionInfoNew ?: encryptionInfo
        set(value) {
            vmState.encryptionInfoNew = value
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
        encryptionInfoNew = when (column) {
            DatabaseColumns.Details ->
                encryptionInfoNew.copy(isFlagsEncrypted = state)

            DatabaseColumns.Name ->
                encryptionInfoNew.copy(isNameEncrypted = state)

            DatabaseColumns.Thumbnail ->
                encryptionInfoNew.copy(isThumbnailEncrypted = state)

            DatabaseColumns.Path ->
                encryptionInfoNew.copy(isPathEncrypted = state)

            DatabaseColumns.EncryptionType ->
                encryptionInfoNew.copy(isEncryptionTypeEncrypted = state)

            DatabaseColumns.ThumbEncryptionType ->
                encryptionInfoNew.copy(isThumbEncryptionTypeEncrypted = state)

            else -> encryptionInfoNew
        }
    }

    private fun isColumnNowSelected(column: DatabaseColumns) = when (column) {
        DatabaseColumns.Details ->
            encryptionInfoNew.isFlagsEncrypted

        DatabaseColumns.Name ->
            encryptionInfoNew.isNameEncrypted

        DatabaseColumns.Thumbnail ->
            encryptionInfoNew.isThumbnailEncrypted

        DatabaseColumns.Path ->
            encryptionInfoNew.isPathEncrypted

        DatabaseColumns.EncryptionType ->
            encryptionInfoNew.isEncryptionTypeEncrypted

        DatabaseColumns.ThumbEncryptionType ->
            encryptionInfoNew.isThumbEncryptionTypeEncrypted

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
                        if (encryptionInfo.isDatabaseEncrypted && encryptionInfo != encryptionInfoNew) {
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
        WorkerFactory.startTransformDatabase(
            oldInfo = encryptionInfo,
            newInfo = encryptionInfoNew
        )
        DatabaseTransformDialog().show(childFragmentManager, null)
        saveEncryptionInfo()
    }

    private fun saveEncryptionInfo() = with(encryptionManager) {
        encryptionInfo = encryptionInfoNew
        save()
    }

}