package com.nevidimka655.astracrypt.view.composables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.nevidimka655.atracrypt.core.design_system.AstraCryptTheme

class CalculatorFragment : Fragment() {

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