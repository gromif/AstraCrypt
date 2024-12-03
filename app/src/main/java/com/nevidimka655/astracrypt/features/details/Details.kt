package com.nevidimka655.astracrypt.features.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.model.CoilTinkModel
import com.nevidimka655.compose_details.Details
import com.nevidimka655.compose_details.DetailsManager
import com.nevidimka655.compose_details.Screen

@Composable
fun DetailsScreen(
    detailsManager: DetailsManager,
    imageLoader: ImageLoader,
    onStart: suspend () -> Unit
) {
    LaunchedEffect(Unit) { onStart() }
    Details.Screen(
        detailsManager = detailsManager,
        headerImage = {
            val thumb = remember {
                detailsManager.extras?.getString("thumb") ?: ""
            }
            val isFile = remember {
                detailsManager.extras?.getBoolean("isFile") == true
            }
            if (thumb.isEmpty()) Icon(
                modifier = Modifier.fillMaxSize(0.5f),
                imageVector = if (isFile) Icons.AutoMirrored.Outlined.InsertDriveFile else Icons.Filled.Folder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            ) else AsyncImage(modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(
                    absolutePath = null,
                    path = thumb
                ),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop)
        }
    )
}