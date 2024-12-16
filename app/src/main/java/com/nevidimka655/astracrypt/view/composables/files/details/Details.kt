package com.nevidimka655.astracrypt.view.composables.files.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.data.model.CoilTinkModel
import com.nevidimka655.astracrypt.data.room.StorageItemType
import com.nevidimka655.compose_details.Details
import com.nevidimka655.compose_details.DetailsManager
import com.nevidimka655.compose_details.Screen

@Composable
fun DetailsScreen(
    detailsManager: DetailsManager,
    preview: String?,
    imageLoader: ImageLoader,
    itemType: StorageItemType,
    onStart: suspend () -> Unit
) {
    LaunchedEffect(Unit) { onStart() }
    Details.Screen(
        detailsManager = detailsManager,
        headerImage = {
            if (preview.isNullOrEmpty()) Icon(
                modifier = Modifier.fillMaxSize(0.5f),
                imageVector = itemType.icon,
                contentDescription = null,
                tint = if (itemType.isFile) Color.Unspecified else MaterialTheme.colorScheme.onSurfaceVariant
            ) else AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = CoilTinkModel(
                    absolutePath = null,
                    path = preview
                ),
                contentDescription = null,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop
            )
        }
    )
}