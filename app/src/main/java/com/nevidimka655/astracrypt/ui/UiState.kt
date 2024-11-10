package com.nevidimka655.astracrypt.ui

data class UiState(
    val navBarEnabled: Boolean = true,
    val movePanelButtonState: Boolean = false,
    val navBarColorTinted: Boolean = true,
    val fabState: Boolean = false,
    val fabLargeState: Boolean = false,
    val toolbarState: Boolean = true,
    val toolbarStateAnimated: Boolean = true
)