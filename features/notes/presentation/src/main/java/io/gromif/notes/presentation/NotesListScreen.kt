package io.gromif.notes.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import io.gromif.ui.compose.core.theme.spaces

@Composable
fun Notes.NotesListScreen(
    onEmptyList: @Composable () -> Unit = {},
    onClick: (id: Long) -> Unit = {}
) {
    val vm: NotesListViewModel = hiltViewModel()
    val items = vm.notesPaging.collectAsLazyPagingItems()
    val showEmptyPage by remember {
        derivedStateOf {
            items.itemCount == 0 && items.loadState.refresh !is LoadState.Loading
        }
    }
    if (showEmptyPage) onEmptyList() else LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        items(
            count = items.itemSnapshotList.size,
            key = { items[it]?.id ?: it }
        ) { index ->
            items[index]?.let {
                Note(
                    title = it.name,
                    summary = it.textPreview,
                    date = it.creationTime
                ) {
                    onClick(it.id)
                }
            }
        }
    }
}

@Preview
@Composable
private fun Note(
    title: String? = "Test title",
    summary: String? = "Test summary...",
    date: String? = "February 6 2023",
    onClick: (() -> Unit) = {}
) = ElevatedCard(
    modifier = Modifier.fillMaxWidth(),
    elevation = CardDefaults.elevatedCardElevation(2.dp)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(MaterialTheme.spaces.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        if (title != null) Text(text = title, style = MaterialTheme.typography.titleMedium)
        if (summary != null) Text(text = summary, style = MaterialTheme.typography.bodyMedium)
        if (date != null) Text(text = date, style = MaterialTheme.typography.labelMedium)
    }
}