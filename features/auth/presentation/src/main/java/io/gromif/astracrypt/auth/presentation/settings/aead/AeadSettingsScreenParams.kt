package io.gromif.astracrypt.auth.presentation.settings.aead

internal data class AeadSettingsScreenParams(
    val settingsOptions: List<String> = listOf("Preview"),
    val settingsOptionIndex: Int = 0,
)