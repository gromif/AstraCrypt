package com.nevidimka655.astracrypt.view.composables.shared.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.view.models.Actions
import com.nevidimka655.ui.compose_core.IconButton
import com.nevidimka655.ui.compose_core.wrappers.TextWrap

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarImpl(
    modifier: Modifier = Modifier,
    title: TextWrap,
    backButton: Boolean,
    actions: Actions?,
    onNavigateUp: () -> Unit,
    onActionPressed: (Actions) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    val context = LocalContext.current
    CenterAlignedTopAppBar(
        title = { Text(text = title.resolve(context)) },
        navigationIcon = {
            if (backButton) IconButton(
                icon = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.back),
                onClick = onNavigateUp
            )
        },
        actions = {
            actions?.list?.forEach {
                IconButton(
                    icon = it.icon,
                    contentDescription = stringResource(id = it.contentDescription),
                    onClick = { onActionPressed(actions) }
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
        modifier = modifier
    )
}