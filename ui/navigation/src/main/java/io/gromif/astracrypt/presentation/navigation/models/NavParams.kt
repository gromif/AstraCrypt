package io.gromif.astracrypt.presentation.navigation.models

data class NavParams(
    val isActionsSupported: Boolean,
    val isDynamicColorsSupported: Boolean,
    val applicationVersion: String
)
