package com.nevidimka655.astracrypt.utils

import android.os.Build

object Api {

    private val sdkInt get() = Build.VERSION.SDK_INT

    fun atLeastAndroid9() = sdkInt >= Build.VERSION_CODES.P
    fun atLeastAndroid8() = sdkInt >= Build.VERSION_CODES.O
    fun atLeastAndroid7() = sdkInt >= Build.VERSION_CODES.N
    fun atLeastAndroid10() = sdkInt >= Build.VERSION_CODES.Q
    fun atLeastAndroid11() = sdkInt >= Build.VERSION_CODES.R
    fun atLeastAndroid13() = sdkInt >= Build.VERSION_CODES.TIRAMISU

}