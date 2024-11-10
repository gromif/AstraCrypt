package com.nevidimka655.astracrypt.tabs.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.BuildConfig
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.EthernetTools
import com.nevidimka655.ui.compose_core.OneLineListItem
import com.nevidimka655.ui.compose_core.PreferencesGroup
import com.nevidimka655.ui.compose_core.PreferencesScreen
import com.nevidimka655.ui.compose_core.TwoLineListItem
import com.nevidimka655.ui.compose_core.ext.vectorResource
import com.nevidimka655.ui.compose_core.theme.spaces

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                AboutScreen()
            }
        }
    }

    @Composable
    private fun AboutScreen() {
        val context = LocalContext.current
        PreferencesScreen {
            AboutTitleCard()
            CommonOptions(context) {
                findNavController().navigate(R.id.action_aboutFragment_to_privacyPolicyFragment)
            }
            PreferencesGroup(text = stringResource(id = R.string.support)) {
                CommunicationOptions(context)
            }
        }
    }

    @Composable
    private fun CommonOptions(
        context: Context, privacyPolicyOnClick: () -> Unit
    ) = Card(modifier = Modifier.fillMaxWidth()) {
        TwoLineListItem(
            titleText = stringResource(id = R.string.about_moreApps),
            summaryText = stringResource(id = R.string.about_moreApps_summary)
        ) {
            EthernetTools.openUri(
                context,
                "https://play.google.com/store/apps/developer?id=Nevidimka655"
            )
        }
        OneLineListItem(
            titleText = stringResource(id = R.string.about_leaveFeedback)
        ) {
            EthernetTools.openAppGooglePlayPage(context)
        }
        TwoLineListItem(
            titleText = "Secure module",
            summaryText = "[none]"
        )
        OneLineListItem(
            titleText = stringResource(id = R.string.privacyPolicy),
            onClick = privacyPolicyOnClick
        )
    }

    @Composable
    private fun CommunicationOptions(context: Context) = Card(modifier = Modifier.fillMaxWidth()) {
        OneLineListItem(titleText = "Email") {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf("alex.kobrys@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            }
            if (intent.resolveActivity(requireActivity().packageManager) != null)
                startActivity(intent)
        }
        OneLineListItem(titleText = "Google Play") {
            EthernetTools.openAppGooglePlayPage(context)
        }
    }

    @Composable
    private fun AboutTitleCard(iconOnClick: () -> Unit = {}) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spaces.spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium)
    ) {
        Image(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .size(96.dp)
                .clickable(onClick = iconOnClick),
            painter = rememberVectorPainter(
                image = vectorResource(id = R.drawable.ic_launcher_foreground)
            ),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "v${BuildConfig.VERSION_NAME}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}