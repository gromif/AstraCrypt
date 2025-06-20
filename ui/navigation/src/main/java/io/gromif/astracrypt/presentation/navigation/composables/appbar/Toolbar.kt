package io.gromif.astracrypt.presentation.navigation.composables.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import io.gromif.astracrypt.presentation.navigation.models.actions.ToolbarActions
import io.gromif.astracrypt.presentation.navigation.models.actions.close
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.IconButton
import io.gromif.ui.compose.core.wrappers.TextWrap
import io.gromif.ui.haptic.Haptic

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarImpl(
    title: TextWrap,
    backButton: Boolean,
    isContextual: Boolean,
    actions: List<ToolbarActions.Action>?,
    onNavigateUp: () -> Unit,
    onActionPressed: (ToolbarActions.Action) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?,
) = DynamicToolbar(
    title = @Composable {
        val context = LocalContext.current
        Text(text = title.resolve(context))
    },
    navigationIcon = @Composable {
        if (backButton) {
            if (!isContextual) {
                IconButton(
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    onClick = {
                        Haptic.click()
                        onNavigateUp()
                    }
                )
            } else {
                IconButton(
                    icon = ToolbarActions.close.icon,
                    contentDescription = stringResource(id = ToolbarActions.close.contentDescription),
                    onClick = {
                        Haptic.click()
                        onActionPressed(ToolbarActions.close)
                    }
                )
            }
        }
    },
    actions = @Composable {
        if (actions != null) {
            val basicActions = remember { actions.take(2) }
            basicActions.forEach {
                IconButton(
                    icon = it.icon,
                    contentDescription = stringResource(id = it.contentDescription),
                ) {
                    Haptic.click()
                    onActionPressed(it)
                }
            }
            if (actions.size > 2) {
                val actionsInDropdownMenu = remember { actions.drop(2) }
                var expanded by remember { mutableStateOf(false) }
                DropdownMenu(expanded, onDismissRequest = { expanded = expanded.not() }) {
                    actionsInDropdownMenu.forEach {
                        DropdownMenuItem(
                            text = { Text(stringResource(it.contentDescription)) },
                            onClick = {
                                onActionPressed(it)
                                expanded = expanded.not()
                            }
                        )
                    }
                }
                IconButton(icon = Icons.Default.MoreVert, contentDescription = null) {
                    expanded = expanded.not()
                }
            }
        }
    },
    isContextual = isContextual,
    scrollBehavior = scrollBehavior
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DynamicToolbar(
    title: @Composable (() -> Unit),
    navigationIcon: @Composable (() -> Unit),
    actions: @Composable (RowScope.() -> Unit),
    isContextual: Boolean,
    scrollBehavior: TopAppBarScrollBehavior?,
) = if (isContextual) {
    TopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
    )
} else {
    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
    )
}
