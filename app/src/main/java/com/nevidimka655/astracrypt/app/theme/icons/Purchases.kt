@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.app.theme.icons

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.ext.vectorResource

val Icons.Purchases get() = _Purchases
object _Purchases

val _Purchases.BackgroundStarter
    @Composable get() = _backgroundStarter
        ?: vectorResource(id = R.drawable.plan_background_starter).also { _backgroundStarter = it }
private var _backgroundStarter: ImageVector? = null

val _Purchases.BackgroundExtreme
    @Composable get() = _backgroundExtreme
        ?: vectorResource(id = R.drawable.plan_background_premium).also { _backgroundExtreme = it }
private var _backgroundExtreme: ImageVector? = null

fun _Purchases.reset() {
    _backgroundStarter = null
    _backgroundExtreme = null
}