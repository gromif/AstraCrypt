package com.nevidimka655.astracrypt.lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.ui.compose_core.theme.spaces

class LabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                LabScreen()
            }
        }
    }

    @Preview
    @Composable
    private fun LabScreen() {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection()),
            columns = GridCells.Adaptive(250.dp),
            contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
        ) {
            item {
                LabItem(
                    imageVector = Icons.Filled.EnhancedEncryption,
                    title = stringResource(id = R.string.lab_aeadEncryption)
                ) { findNavController().navigate(R.id.action_labFragment_to_aeadFragment) }
            }
            item {
                LabItem(
                    imageVector = Icons.Filled.Archive,
                    title = stringResource(id = R.string.lab_combinedZip)
                ) { findNavController().navigate(R.id.action_labFragment_to_archiveFragment) }
            }
        }
    }

    @Preview
    @Composable
    fun LabItem(
        imageVector: ImageVector = Icons.Default.Search,
        title: String = "Some title here",
        clickCallback: () -> Unit = {}
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable(onClick = clickCallback)
                    .padding(MaterialTheme.spaces.spaceSmall),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Box(modifier = Modifier.size(64.dp)) {
                    Icon(
                        modifier = Modifier.size(40.dp).align(Alignment.Center),
                        painter = rememberVectorPainter(image = imageVector),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}