package com.nevidimka655.astracrypt.app.utils

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

object Api {

    private val sdkInt = Build.VERSION.SDK_INT

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun atLeast8() = sdkInt >= Build.VERSION_CODES.O

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.P)
    fun atLeast9() = sdkInt >= Build.VERSION_CODES.P

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.N)
    fun atLeast7() = sdkInt >= Build.VERSION_CODES.N

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.Q)
    fun atLeast10() = sdkInt >= Build.VERSION_CODES.Q

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    fun atLeast11() = sdkInt >= Build.VERSION_CODES.R

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
    fun atLeast12() = sdkInt >= Build.VERSION_CODES.S

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.TIRAMISU)
    fun atLeast13() = sdkInt >= Build.VERSION_CODES.TIRAMISU

}