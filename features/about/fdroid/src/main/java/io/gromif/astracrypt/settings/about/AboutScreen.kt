package io.gromif.astracrypt.settings.about

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.nevidimka655.astracrypt.resources.R
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    snackbarHostState: SnackbarHostState,
    version: String,
    toPrivacyPolicy: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    fun open(uri: Uri) = context.startActivity(Intent(Intent.ACTION_VIEW, uri))

    Screen(
        params = Params(
            version = version,
            storeName = "F-Droid"
        ),
        onMoreApps = {
            open(Config.MORE_APPS_PAGE)
        },
        onLeaveFeedback = {
            open(Config.APP_PAGE)
        },
        onEmailClick = {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(Config.EMAIL))
                putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
            }
            try {
                context.startActivity(intent)
            } catch (_: ActivityNotFoundException) {
                scope.launch {
                    snackbarHostState.showSnackbar(context.getString(R.string.error))
                }
            }
        },
        onMarketClick = {
            open(Config.APP_PAGE)
        },
        toPrivacyPolicy = toPrivacyPolicy
    )
}