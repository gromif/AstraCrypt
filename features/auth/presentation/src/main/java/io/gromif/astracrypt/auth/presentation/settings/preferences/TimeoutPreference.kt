package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.auth.domain.model.Timeout
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.radio

@Composable
internal fun TimeoutPreference(
    currentTimeout: Timeout = Timeout.NEVER,
    onSetTimeout: (Timeout) -> Unit
) {
    val options = getTimeoutOptionsList()

    var dialogTimeoutState by DialogsCore.Selectable.radio(
        onSelected = {
            val timeout = Timeout.entries[it]
            onSetTimeout(timeout)
        },
        title = stringResource(id = R.string.timeout),
        items = options,
        selectedItemIndex = currentTimeout.ordinal
    )

    Preference(
        titleText = stringResource(id = R.string.timeout),
        summaryText = options[currentTimeout.ordinal]
    ) { dialogTimeoutState = true }
}

@Composable
private fun getTimeoutOptionsList(): List<String> {
    val context = LocalContext.current

    return remember {
        Timeout.entries.map {
            when(it) {
                Timeout.IMMEDIATELY -> context.getString(R.string.immediately)
                Timeout.NEVER -> context.getString(R.string.never)
                else -> context.resources.getQuantityString(R.plurals.plural_seconds, it.seconds, it.seconds)
            }
        }
    }
}