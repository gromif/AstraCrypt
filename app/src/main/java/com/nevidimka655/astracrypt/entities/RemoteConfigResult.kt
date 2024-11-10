package com.nevidimka655.astracrypt.entities

data class RemoteConfigResult(
    val adsStatus: Boolean,
    val isUpdateAvailable: Boolean,
    val isDeprecated: Boolean,
    val isUkraineBased: Boolean
)