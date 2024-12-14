package com.nevidimka655.astracrypt.features.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.view.MainVM

class CalculatorFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                /*LaunchedEffect(Unit) {
                    vm.toolsManager.calculatorManager.onCamouflagesClose.consumeEach {
                        if (it && !vm.updateCamouflageFeatureAccess()) findNavController().navigate(
                            when (vm.authManager.info.authType) {
                                AuthType.NO_AUTH -> R.id.action_global_homeFragment
                                AuthType.PASSWORD -> R.id.action_global_authFragment
                            }
                        )
                    }
                }
                Calculator.Screen.Auto(calculatorManager = vm.toolsManager.calculatorManager)*/
            }
        }
    }
}