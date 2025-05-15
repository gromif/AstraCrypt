package io.gromif.astracrypt.utils

import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import contract.secureContent.SecureContentContract

fun Modifier.secureContent(
    mode: SecureContentContract.Mode,
    windowHasFocus: Boolean
): Modifier {
    return if (mode == SecureContentContract.Mode.FORCE && !windowHasFocus) {
        if (Api.atLeast12()) this.blur(35.dp) else this.alpha(0f)
    } else {
        this
    }
}

@Suppress("detekt:FunctionNaming")
@Composable
fun SetSecureContentFlag(mode: SecureContentContract.Mode, window: Window) {
    LaunchedEffect(mode) {
        val secureFlag = WindowManager.LayoutParams.FLAG_SECURE
        if (mode != SecureContentContract.Mode.DISABLED) {
            window.addFlags(secureFlag)
        } else {
            window.clearFlags(secureFlag)
        }
    }
}
