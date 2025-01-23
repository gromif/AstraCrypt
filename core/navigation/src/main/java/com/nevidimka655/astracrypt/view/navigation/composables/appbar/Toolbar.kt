package com.nevidimka655.astracrypt.view.navigation.composables.appbar

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.resources.R
import com.nevidimka655.astracrypt.view.navigation.models.actions.ToolbarActions
import com.nevidimka655.ui.compose_core.IconButton
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarImpl(
    modifier: Modifier = Modifier,
    title: TextWrap,
    backButton: Boolean,
    isContextual: Boolean,
    actions: List<ToolbarActions.Action>?,
    onNavigateUp: () -> Unit,
    onActionPressed: (ToolbarActions.Action) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?
) = if (isContextual) TopAppBar(
    title = { Title(title) },
    navigationIcon = { if (backButton) ActionBack(onNavigateUp) },
    actions = {
        if (actions != null) Actions(actions, onActionPressed)
    },
    scrollBehavior = scrollBehavior,
    colors = TopAppBarDefaults.topAppBarColors().copy(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        actionIconContentColor = MaterialTheme.colorScheme.onSurface
    ),
    modifier = modifier
) else CenterAlignedTopAppBar(
    title = { Title(title) },
    navigationIcon = { if (backButton) ActionBack(onNavigateUp) },
    actions = {
        if (actions != null) Actions(actions, onActionPressed)
    },
    scrollBehavior = scrollBehavior,
    modifier = modifier
)

@Composable
private fun Title(title: TextWrap) {
    val context = LocalContext.current
    Text(text = title.resolve(context))
}

@Composable
private fun ActionBack(onNavigateUp: () -> Unit) = IconButton(
    icon = Icons.AutoMirrored.Default.ArrowBack,
    contentDescription = stringResource(id = R.string.back),
    onClick = onNavigateUp
)

@Composable
private fun Actions(
    actions: List<ToolbarActions.Action>,
    onActionPressed: (ToolbarActions.Action) -> Unit
) = actions.forEach {
    IconButton(
        icon = it.icon,
        contentDescription = stringResource(id = it.contentDescription),
        onClick = { onActionPressed(it) }
    )
}