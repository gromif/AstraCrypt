package io.gromif.astracrypt.settings.about.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.compose.material3.SnackbarHostState
import androidx.core.net.toUri
import io.gromif.astracrypt.resources.R
import io.gromif.astracrypt.settings.about.model.Link
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun Link.Default.start(context: Context) {
    context.startActivity(Intent(Intent.ACTION_VIEW, link.toUri()))
}

internal fun Link.Email.start(
    context: Context,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
    }
    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        scope.launch {
            snackbarHostState.showSnackbar(context.getString(R.string.error))
        }
    }
}
