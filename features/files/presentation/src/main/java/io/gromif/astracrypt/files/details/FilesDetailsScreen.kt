package io.gromif.astracrypt.files.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nevidimka655.compose_details.DetailsScreen
import io.gromif.astracrypt.files.domain.model.FileType
import io.gromif.astracrypt.files.domain.model.ItemDetails
import io.gromif.astracrypt.files.shared.icon

@Composable
fun FilesDetailsScreen(id: Long) {
    val context = LocalContext.current
    val vm: DetailsScreenViewModel = hiltViewModel()
    val detailsStateList = vm.detailsStateList

    LaunchedEffect(Unit) {
        if (detailsStateList.isEmpty()) vm.loadItemDetails(context, id)
    }
    val itemDetails = vm.itemDetails

    if (itemDetails != null) DetailsScreen(
        groups = detailsStateList,
        headerImage = {
            if (itemDetails is ItemDetails.File) {
                if (itemDetails.preview == null) Icon(
                    modifier = Modifier.fillMaxSize(0.5f),
                    imageVector = itemDetails.type.icon,
                    contentDescription = null,
                    tint = Color.Unspecified
                ) else AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = itemDetails.preview,
                    contentDescription = null,
                    imageLoader = vm.imageLoader,
                    contentScale = ContentScale.Crop
                )
            } else Icon(
                modifier = Modifier.fillMaxSize(0.5f),
                imageVector = FileType.Folder.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        title = itemDetails.itemName,
    )
}
