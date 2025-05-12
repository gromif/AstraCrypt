package io.gromif.ui.compose.core

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWithToolbar(
    isVisibleState: MutableState<Boolean>,
    sheetState: SheetState,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    if (isVisibleState.value) ModalBottomSheet(
        onDismissRequest = { isVisibleState.value = !isVisibleState.value },
        sheetState = sheetState
    ) {
        CenterAlignedTopAppBar(title = { Text(text = title) })
        content()
        Spacer(modifier = Modifier.navigationBarsPadding())
    }
}