package com.nevidimka655.astracrypt.features.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nevidimka655.astracrypt.help.HelpFragmentArgs
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.compose_help.Help
import com.nevidimka655.compose_help.HelpScreen

class HelpFragment : Fragment() {
    private val navArgs by navArgs<HelpFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                val textIdsList = remember {
                    when(HelpIndex.entries[navArgs.helpIndex]) {
                        HelpIndex.SETTINGS_ENCRYPTION -> Help.settingsSecurityEncryption()
                        HelpIndex.LAB_COMBINED_ZIP -> Help.labZip()
                    }
                }
                HelpScreen(list = textIdsList)
            }
        }
    }

    companion object {
        const val ARGS_HELP_INDEX_NAME = "help_index"
    }

}