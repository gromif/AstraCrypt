package io.gromif.astracrypt.files.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AeadInfoDto(

    @SerialName("a")
    val fileAeadIndex: Int = -1,

    @SerialName("b")
    val previewAeadIndex: Int = -1,

    @SerialName("c")
    val databaseAeadIndex: Int = -1,

    @SerialName("d")
    val isNameColumnEncrypted: Boolean = false,

    @SerialName("e")
    val isPreviewColumnEncrypted: Boolean = false,

    @SerialName("f")
    val isPathColumnEncrypted: Boolean = false,

    @SerialName("g")
    val isFlagColumnEncrypted: Boolean = false

)