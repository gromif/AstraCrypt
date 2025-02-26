package io.gromif.astracrypt.files.settings.aead

import io.gromif.astracrypt.files.domain.model.AeadInfo

internal data class AeadSettingsScreenParams(
    val aeadInfo: AeadInfo = AeadInfo(),

    val fileOptions: List<String> = listOf("Test"),
    val fileAeadIndex: Int = 0,

    val previewOptions: List<String> = fileOptions,
    val previewAeadIndex: Int = fileAeadIndex,

    val databaseOptions: List<String> = fileOptions,
    val databaseAeadIndex: Int = fileAeadIndex,
)
