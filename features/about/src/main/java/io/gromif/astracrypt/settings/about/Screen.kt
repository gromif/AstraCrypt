package io.gromif.astracrypt.settings.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.list.LinkList
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.model.Params
import io.gromif.astracrypt.settings.about.shared.CardLinkList
import io.gromif.astracrypt.settings.about.shared.Header
import io.gromif.ui.compose.core.PreferenceDefaults
import io.gromif.ui.compose.core.PreferencesGroupContent
import io.gromif.ui.compose.core.PreferencesGroupTitle

@Preview(showBackground = true)
@Composable
internal fun Screen(
    modifier: Modifier = Modifier,
    columns: Int = PreferenceDefaults.Screen.columns,
    params: Params = Params(),
    onLinkClick: (Link) -> Unit = {},
) = LazyVerticalGrid(
    columns = GridCells.Fixed(columns),
    horizontalArrangement = PreferenceDefaults.Screen.horizontalArrangement,
    verticalArrangement = PreferenceDefaults.Screen.verticalArrangement,
    contentPadding = PreferenceDefaults.Screen.contentPadding,
    modifier = modifier
) {
    item { Header(version = params.version) }
    item { CardLinkList(links = params.commonLinks, onLinkClick = onLinkClick) }
    item {
        Column(verticalArrangement = PreferenceDefaults.Screen.verticalArrangement) {
            PreferencesGroupTitle(text = stringResource(id = R.string.support))
            PreferencesGroupContent {
                LinkList(links = params.supportLinks, onClick = onLinkClick)
            }
        }
    }
}
