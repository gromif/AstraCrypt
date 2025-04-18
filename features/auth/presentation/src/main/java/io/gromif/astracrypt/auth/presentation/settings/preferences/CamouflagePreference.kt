package io.gromif.astracrypt.auth.presentation.settings.preferences

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.resources.R
import io.gromif.ui.compose.core.Preference
import io.gromif.ui.compose.core.dialogs.DialogsCore
import io.gromif.ui.compose.core.dialogs.default
import io.gromif.ui.compose.core.dialogs.radio

@Composable
internal fun CamouflagePreference(
    currentSkinType: SkinType?,
    onDisableCamouflage: () -> Unit,
    onSetCalculatorCamouflage: (String) -> Unit
) {
    val options = getCamouflageOptionsList()
    val currentOptionIndex = remember(currentSkinType) {
        currentSkinType?.let { it.ordinal + 1 } ?: 0
    }

    var dialogCalculatorCombination by dialogCalculatorCombination(onSetCalculatorCamouflage)
    var dialogSkinMethodsState by dialogCamouflageMethods(
        options = options,
        skinIndex = currentOptionIndex
    ) {
        if (it != currentOptionIndex) when (it) {
            0 -> onDisableCamouflage()
            1 -> dialogCalculatorCombination = true
        }
    }

    Preference(
        titleText = stringResource(id = R.string.settings_camouflage),
        summaryText = options[currentOptionIndex]
    ) { dialogSkinMethodsState = true }
}

@Composable
private fun getCamouflageOptionsList(): List<String> {
    val context = LocalContext.current

    return remember {
        listOf(
            context.getString(R.string.settings_camouflageType_no),
            context.getString(R.string.settings_camouflageType_calculator),
        )
    }
}

@Composable
private fun dialogCamouflageMethods(
    options: List<String>,
    skinIndex: Int,
    onSelected: (Int) -> Unit
): MutableState<Boolean> = DialogsCore.Selectable.radio(
    onSelected = onSelected,
    title = stringResource(id = R.string.settings_camouflage),
    items = options,
    selectedItemIndex = skinIndex
)

@Composable
private fun dialogCalculatorCombination(
    onResult: (String) -> Unit
) = DialogsCore.TextFields.default(
    title = stringResource(id = R.string.settings_camouflage_numberCombination),
    params = DialogsCore.TextFields.Params(
        label = stringResource(id = R.string.settings_camouflage_numberCombination),
        selectAllText = true,
        //maxLength = CalculatorManager.MAX_NUM_LENGTH,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
    ),
    onResult = onResult
)