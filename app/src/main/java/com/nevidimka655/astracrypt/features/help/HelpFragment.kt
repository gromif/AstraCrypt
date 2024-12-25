package com.nevidimka655.astracrypt.features.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.nevidimka655.atracrypt.core.design_system.AstraCryptTheme

class HelpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                /*val textIdsList = remember {
                    when(HelpIndex.entries[navArgs.helpIndex]) {
                        HelpIndex.SETTINGS_ENCRYPTION -> Help.settingsSecurityEncryption()
                        HelpIndex.LAB_COMBINED_ZIP -> Help.labZip()
                    }
                }
                HelpScreen(list = textIdsList)*/
            }
        }
    }

    companion object {
        const val ARGS_HELP_INDEX_NAME = "help_index"
    }

}