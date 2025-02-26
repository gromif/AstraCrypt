package io.gromif.astracrypt.files.settings.aead

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastMap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nevidimka655.astracrypt.resources.R
import io.gromif.astracrypt.files.domain.model.AeadMode
import io.gromif.crypto.tink.extensions.getTemplateList
import io.gromif.crypto.tink.extensions.getTemplateList1MB
import io.gromif.crypto.tink.extensions.getTemplateList4KB
import io.gromif.crypto.tink.model.AeadTemplate
import io.gromif.crypto.tink.model.KeysetTemplates

@Composable
fun FilesAeadSettings(
    modifier: Modifier = Modifier,
    toDatabaseColumnsAeadSettings: () -> Unit,
) {
    val vm: AeadSettingsViewModel = hiltViewModel()
    val aeadInfoState by vm.aeadInfoState.collectAsStateWithLifecycle()

    val aeadTemplateList = remember { KeysetTemplates.AEAD.getTemplateList() }
    val aead1MbTemplateList = remember { KeysetTemplates.Stream.getTemplateList1MB() }
    val aead4KbTemplateList = remember { KeysetTemplates.Stream.getTemplateList4KB() }

    val withoutEncryption = stringResource(R.string.withoutEncryption)
    val aeadList = remember {
        listOf(withoutEncryption) + aeadTemplateList.fastMap { it.name }
    }
    val aead1MbList = remember {
        listOf(withoutEncryption) + aead1MbTemplateList.fastMap { it.name }
    }
    val aead4KbList = remember {
        listOf(withoutEncryption) + aead4KbTemplateList.fastMap { it.name }
    }

    val fileMode = aeadInfoState.fileMode
    val filesAeadIndex = remember(fileMode) { aead1MbTemplateList.indexOf(fileMode) }

    val previewMode = aeadInfoState.previewMode
    val previewAeadIndex = remember(previewMode) { aead4KbTemplateList.indexOf(previewMode) }

    val databaseMode = aeadInfoState.databaseMode
    val databaseAeadIndex = remember(databaseMode) { aeadTemplateList.indexOf(databaseMode) }
    AeadSettingsScreen(
        modifier = modifier,
        params = AeadSettingsScreenParams(
            aeadInfo = aeadInfoState,
            fileOptions = aead1MbList,
            fileAeadIndex = filesAeadIndex,
            previewOptions = aead4KbList,
            previewAeadIndex = previewAeadIndex,
            databaseOptions = aeadList,
            databaseAeadIndex = databaseAeadIndex
        ),
        onFileAeadChange = {
            val aeadMode = aead1MbTemplateList.parseAeadIndex(it)
            vm.setFileMode(aeadMode)
        },
        onPreviewAeadChange = {
            val aeadMode = aead4KbTemplateList.parseAeadIndex(it)
            vm.setPreviewMode(aeadMode)
        },
        onDatabaseAeadChange = {
            val aeadMode = aeadTemplateList.parseAeadIndex(it)
            vm.setDatabaseMode(aeadMode)
        },
        toDatabaseColumnsAeadSettings = toDatabaseColumnsAeadSettings
    )
}

private fun List<AeadTemplate>.indexOf(mode: AeadMode): Int {
    return if (mode is AeadMode.None) 0 else {
        indexOfFirst { it.id == mode.id } + 1
    }
}

private fun List<AeadTemplate>.parseAeadIndex(index: Int): AeadMode {
    return if (index == 0) AeadMode.None else {
        val originalIndex = index - 1
        val aeadTemplate = get(originalIndex)
        AeadMode.Template(id = aeadTemplate.id, name = aeadTemplate.name)
    }
}