package com.nevidimka655.astracrypt.view.composables.shared.appbar

import android.R
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarImpl(
    modifier: Modifier = Modifier,
    query: String,
    onSearch: (String) -> Unit,
    onQueryChange: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    backButtonEnabled: Boolean
) = SearchBar(
    inputField = {
        SearchBarDefaults.InputField(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            placeholder = {
                Text(text = stringResource(id = R.string.search_go))
            },
            leadingIcon = {
                val localBackDispatcher = LocalOnBackPressedDispatcherOwner.current
                val searchIcon = Icons.Default.Search
                val backIcon = Icons.AutoMirrored.Default.ArrowBack
                if (!backButtonEnabled) Icon(searchIcon, null)
                else IconButton(
                    onClick = { localBackDispatcher?.onBackPressedDispatcher?.onBackPressed() }
                ) {
                    Icon(backIcon, null)
                }
            }
        )
    },
    expanded = expanded,
    onExpandedChange = onExpandedChange,
    modifier = modifier
) { }