package io.gromif.notes.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.resources.R
import io.gromif.crypto.tink.keyset.AeadTemplate
import io.gromif.notes.domain.model.AeadMode
import io.gromif.notes.presentation.settings.shared.DatabaseOption
import io.gromif.ui.compose.core.PreferencesGroup
import io.gromif.ui.compose.core.theme.spaces

@Composable
internal fun AeadSettingsUi(
    modifier: Modifier = Modifier,
    aeadMode: AeadMode? = AeadMode.None,
    aeadTemplatesList: List<AeadTemplate> = listOf(),
    onNotesAeadChange: (AeadMode) -> Unit = {},
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    PreferencesGroup(text = stringResource(id = R.string.notes)) {
        if (aeadMode != null) DatabaseOption(
            aeadMode = aeadMode,
            aeadTemplatesList = aeadTemplatesList,
            onNotesAeadChange = onNotesAeadChange
        )
    }
}