package com.nevidimka655.astracrypt.features.help

class HelpFragment{

    /*override fun onCreateView(
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
    }*/

    companion object {
        const val ARGS_HELP_INDEX_NAME = "help_index"
    }

}