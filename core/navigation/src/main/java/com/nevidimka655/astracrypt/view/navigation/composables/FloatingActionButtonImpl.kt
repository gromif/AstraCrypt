package com.nevidimka655.astracrypt.view.navigation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingActionButtonImpl(
    visible: Boolean,
    imageVector: ImageVector?,
    contentDescription: String?,
    onFabClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut()
    ) {
        LargeFloatingActionButton(
            onClick = onFabClick,
        ) { if (imageVector != null) Icon(imageVector, contentDescription) }
    }
}