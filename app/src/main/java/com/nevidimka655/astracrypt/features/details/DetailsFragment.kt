package com.nevidimka655.astracrypt.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.compose.AsyncImage
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.entities.CoilTinkModel
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.Engine
import com.nevidimka655.compose_details.Details
import com.nevidimka655.compose_details.Screen

class DetailsFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                Surface {
                    val detailsManager = vm.toolsManager.detailsManager
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
                            ) else {
                                val encryption = remember {
                                    detailsManager.extras?.getInt("encryption") ?: -1
                                }
                                AsyncImage(modifier = Modifier.fillMaxSize(),
                                    model = CoilTinkModel(
                                        absolutePath = null,
                                        path = thumb,
                                        encryptionType = encryption
                                    ),
                                    contentDescription = null,
                                    imageLoader = Engine.imageLoader,
                                    contentScale = ContentScale.Crop)
                            }
                        }
                    )
                }
            }
        }
    }
}