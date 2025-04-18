package io.gromif.secure_content.presentation

import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import io.gromif.astracrypt.utils.Api
import io.gromif.secure_content.domain.SecureContentMode

fun Modifier.secureContent(
    mode: SecureContentMode,
    windowHasFocus: Boolean
): Modifier {
    return if (mode == SecureContentMode.FORCE && !windowHasFocus) {
        if (Api.atLeast12()) this.blur(35.dp) else this.alpha(0f)
    }
    else this
}

@Composable
fun SetSecureContentFlag(mode: SecureContentMode, window: Window) {
    LaunchedEffect(mode) {
        val secureFlag = WindowManager.LayoutParams.FLAG_SECURE
        if (mode != SecureContentMode.DISABLED) window.addFlags(secureFlag)
        else window.clearFlags(secureFlag)
    }
}