package com.nevidimka655.astracrypt.view.navigation.composables.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.ui.compose_core.IconButton
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

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
        if (backButton) IconButton(
            icon = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            onClick = onNavigateUp
        )
    },
    actions = @Composable {
        actions?.forEach {
            IconButton(
                icon = it.icon,
                contentDescription = stringResource(id = it.contentDescription),
                onClick = { onActionPressed(it) }
            )
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
) = if (isContextual) TopAppBar(
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
) else CenterAlignedTopAppBar(
    title = title,
    navigationIcon = navigationIcon,
    actions = actions,
    scrollBehavior = scrollBehavior,
)