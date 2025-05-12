package io.gromif.ui.compose.core.sheets

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import io.gromif.ui.compose.core.Compose
import io.gromif.ui.compose.core.theme.spaces

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetDefaults.default(
    onDismissRequest: (() -> Unit)? = null,
    state: MutableState<Boolean> = Compose.state(),
    sheetState: SheetState,
    title: String? = null,
    content: @Composable (ColumnScope.() -> Unit)
) = base(
    onDismissRequest = onDismissRequest,
    state = state,
    sheetState = sheetState
) {
    if (title != null) {
        SheetDefaults.Components.Title(text = title)
        Spacer(modifier = Modifier.Companion.height(MaterialTheme.spaces.spaceMedium))
    }
    content()
    Spacer(modifier = Modifier.navigationBarsPadding())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetDefaults.base(
    onDismissRequest: (() -> Unit)? = null,
    state: MutableState<Boolean> = Compose.state(),
    sheetState: SheetState,
    modifier: Modifier = Modifier,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = BottomSheetDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    tonalElevation: Dp = BottomSheetDefaults.Elevation,
    scrimColor: Color = BottomSheetDefaults.ScrimColor,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    content: @Composable (ColumnScope.() -> Unit)
): MutableState<Boolean> {
    if (state.value) ModalBottomSheet(
        onDismissRequest = {
            state.value = false
            onDismissRequest?.invoke()
        },
        modifier = modifier,
        sheetState = sheetState,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        scrimColor = scrimColor,
        dragHandle = dragHandle,
        content = content
    )
    return state
}