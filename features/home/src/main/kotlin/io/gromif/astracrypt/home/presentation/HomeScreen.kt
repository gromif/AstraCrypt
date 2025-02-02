package io.gromif.astracrypt.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.theme.spaces
import io.gromif.astracrypt.files.recent.RecentFilesComponent
import io.gromif.astracrypt.files.recent.list.Actions
import io.gromif.astracrypt.profile.presentation.shared.Profile
import io.gromif.astracrypt.profile.presentation.widget.WidgetComponent

@Composable
fun HomeScreen(
    recentFilesActions: Actions,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(MaterialTheme.spaces.spaceMedium),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
) {
    Profile.WidgetComponent()
    CardWithTitle(titleText = stringResource(id = R.string.recentlyAdded)) {
        RecentFilesComponent(actions = recentFilesActions)
    }
}