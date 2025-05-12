package io.gromif.ui.compose.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.Dp
import io.gromif.ui.compose.core.ext.LocalWindowWidth
import io.gromif.ui.compose.core.ext.isCompact
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun TwoPanelLayout(
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
    padding: Dp = MaterialTheme.spaces.spaceMedium
) {
    if (LocalWindowWidth.current.isCompact) Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(padding)
    ) {
        left()
        right()
    } else Row(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding),
        horizontalArrangement = Arrangement.spacedBy(padding)
    ) {
        Row(modifier = Modifier.weight(0.5f)) {
            left()
        }
        Row(modifier = Modifier.weight(0.5f)) {
            right()
        }
    }
}

@Composable
fun TwoPanelLayoutInnerScroll(
    left: @Composable (ColumnScope.() -> Unit),
    right: @Composable (ColumnScope.() -> Unit),
    padding: Dp = MaterialTheme.spaces.spaceMedium
) {
    if (LocalWindowWidth.current.isCompact) Column(
        Modifier
            .fillMaxSize()
            .nestedScroll(rememberNestedScrollInteropConnection())
            .verticalScroll(rememberScrollState())
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(padding)
    ) {
        left()
        right()
    } else Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalArrangement = Arrangement.spacedBy(padding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f)
                .nestedScroll(rememberNestedScrollInteropConnection())
                .verticalScroll(rememberScrollState()),
            content = left
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f)
                .nestedScroll(rememberNestedScrollInteropConnection())
                .verticalScroll(rememberScrollState()),
            content = right
        )
    }
}