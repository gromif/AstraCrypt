package io.gromif.astracrypt.profile.presentation.settings.aead

internal data class AeadSettingsScreenParams(
    val settingsOptions: List<String> = listOf("Preview"),
    val settingsOptionIndex: Int = 0,
)