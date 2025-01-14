package io.gromif.astracrypt.files.domain.model

data class AeadInfo(
    val fileAeadIndex: Int = -1,
    val previewAeadIndex: Int = -1,
    val databaseAeadIndex: Int = -1,
    val isNameColumnEncrypted: Boolean = false,
    val isPreviewColumnEncrypted: Boolean = false,
    val isPathColumnEncrypted: Boolean = false,
    val isFlagColumnEncrypted: Boolean = false
)