package io.gromif.astracrypt.files.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AeadInfoDto(

    @SerialName("a")
    val fileAeadIndex: Int,

    @SerialName("b")
    val previewAeadIndex: Int,

    @SerialName("c")
    val databaseAeadIndex: Int,

    @SerialName("d")
    val isNameColumnEncrypted: Boolean,

    @SerialName("e")
    val isPreviewColumnEncrypted: Boolean,

    @SerialName("f")
    val isPathColumnEncrypted: Boolean,

    @SerialName("g")
    val isFlagColumnEncrypted: Boolean

)