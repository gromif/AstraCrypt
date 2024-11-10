@file:Suppress("ObjectPropertyName")

package com.nevidimka655.astracrypt.ui.theme.icons

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.nevidimka655.astracrypt.R
import com.nevidimka655.ui.compose_core.ext.vectorResource

val Icons.Avatars get() = _Avatars
object _Avatars

val _Avatars.Avatar1
    @Composable get() = avatar1
        ?: vectorResource(id = R.drawable.avatar_1).also { avatar1 = it }
private var avatar1: ImageVector? = null

val _Avatars.Avatar2
    @Composable get() = avatar2
        ?: vectorResource(id = R.drawable.avatar_2).also { avatar2 = it }
private var avatar2: ImageVector? = null

val _Avatars.Avatar3
    @Composable get() = avatar3
        ?: vectorResource(id = R.drawable.avatar_3).also { avatar3 = it }
private var avatar3: ImageVector? = null

val _Avatars.Avatar4
    @Composable get() = avatar4
        ?: vectorResource(id = R.drawable.avatar_4).also { avatar4 = it }
private var avatar4: ImageVector? = null

val _Avatars.Avatar5
    @Composable get() = avatar5
        ?: vectorResource(id = R.drawable.avatar_5).also { avatar5 = it }
private var avatar5: ImageVector? = null

val _Avatars.Avatar6
    @Composable get() = avatar6
        ?: vectorResource(id = R.drawable.avatar_6).also { avatar6 = it }
private var avatar6: ImageVector? = null

fun _Avatars.reset() {
    avatar1 = null
    avatar2 = null
    avatar3 = null
    avatar4 = null
    avatar5 = null
    avatar6 = null
}