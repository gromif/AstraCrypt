package io.gromif.tink_lab.presentation.key

import androidx.compose.runtime.Stable
import io.gromif.tink_lab.domain.model.DataType

@Stable
internal data class KeyScreenState(
    val uiMode: UiMode = UiMode.CreateKey,

    val fileAeadList: List<String> = listOf(),
    val textAeadList: List<String> = listOf(),

    val dataTypes: List<DataType> = listOf(),
    val dataType: DataType = DataType.Files,
    val aeadType: String = "TEST_AEAD_TYPE",
    val keysetKey: String = "",
    val isWrongPassword: Boolean = false,
)