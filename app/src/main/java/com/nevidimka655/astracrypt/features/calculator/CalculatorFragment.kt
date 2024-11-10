package com.nevidimka655.astracrypt.features.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.features.auth.AuthType
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.compose_calculator.Calculator
import com.nevidimka655.compose_calculator.ui.Auto
import kotlinx.coroutines.channels.consumeEach

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
                LaunchedEffect(Unit) {
                    vm.toolsManager.calculatorManager.onCamouflagesClose.consumeEach {
                        if (it && !vm.updateCamouflageFeatureAccess()) findNavController().navigate(
                            when (vm.authManager.info.authType) {
                                AuthType.NO_AUTH -> R.id.action_global_homeFragment
                                AuthType.PASSWORD -> R.id.action_global_authFragment
                            }
                        )
                    }
                }
                Calculator.Screen.Auto(calculatorManager = vm.toolsManager.calculatorManager)
            }
        }
    }
}