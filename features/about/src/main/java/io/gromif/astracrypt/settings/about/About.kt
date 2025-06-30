package io.gromif.astracrypt.settings.about

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.gromif.astracrypt.settings.about.extensions.start
import io.gromif.astracrypt.settings.about.model.Link
import io.gromif.astracrypt.settings.about.model.Params
import kotlinx.coroutines.CoroutineScope

@Preview(showBackground = true)
@Composable
fun About(
    params: Params = Params(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    toPrivacyPolicy: () -> Unit = {},
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val openLinkLambda = buildLinkClick(
        context = context,
        scope = scope,
        snackbarHostState = snackbarHostState,
        toPrivacyPolicy = toPrivacyPolicy
    )
    Screen(params = params, onLinkClick = openLinkLambda)
}

private fun buildLinkClick(
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    toPrivacyPolicy: () -> Unit
): (Link) -> Unit = {
    when (it) {
        is Link.Default -> it.start(context)
        is Link.Email -> it.start(
            context = context,
            scope = scope,
            snackbarHostState = snackbarHostState
        )
        Link.PrivacyPolicy -> toPrivacyPolicy()
    }
}
