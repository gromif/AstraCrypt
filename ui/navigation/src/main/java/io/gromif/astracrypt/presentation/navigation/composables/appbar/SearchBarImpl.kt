package io.gromif.astracrypt.presentation.navigation.composables.appbar

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarImpl(
    query: String = "Search",
    onSearch: (String) -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    backButtonEnabled: Boolean = true,
) = Box(
    Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .semantics { isTraversalGroup = true }
) {
    BackHandler(enabled = query.isNotEmpty()) { onQueryChange("") }
    SearchBar(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = android.R.string.search_go)
                    )
                },
                leadingIcon = {
                    val localBackDispatcher = LocalOnBackPressedDispatcherOwner.current
                    val searchIcon = Icons.Default.Search
                    val backIcon = Icons.AutoMirrored.Default.ArrowBack
                    if (!backButtonEnabled) {
                        Icon(searchIcon, null)
                    } else {
                        IconButton(
                            onClick = { localBackDispatcher?.onBackPressedDispatcher?.onBackPressed() }
                        ) {
                            Icon(backIcon, null)
                        }
                    }
                }
            )
        },
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) { }
}
