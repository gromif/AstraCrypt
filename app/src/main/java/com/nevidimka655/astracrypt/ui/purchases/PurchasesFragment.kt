package com.nevidimka655.astracrypt.ui.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.EthernetTools
import com.nevidimka655.astracrypt.utils.billing.PlayBillingManager
import com.nevidimka655.astracrypt.utils.billing.PurchaseManager
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.launch

class PurchasesFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                SubscriptionsScreen()
            }
        }
    }

    @Composable
    fun SubscriptionsScreen() {
        val isPremiumPurchased = remember { vm.purchaseManager.isPremium }
        val cellsCount = LocalWindowWidth.current.cellsCount()
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection()),
            columns = GridCells.Fixed(cellsCount),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
            contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium)
        ) {
            item {
                PurchaseCard { planNameRes ->
                    openPlan(planNameRes, PurchaseManager.Purchase.Basic)
                }
            }
            item {
                PurchasePremiumCard(isPurchased = isPremiumPurchased) { planNameRes ->
                    openPlan(planNameRes, PurchaseManager.Purchase.Premium)
                }
            }
        }
    }

    private fun openPlan(planNameRes: Int, purchase: PurchaseManager.Purchase) {
        if (!PlayBillingManager.isServicesAvailable(requireContext()) ||
            !EthernetTools.isConnected(requireContext())
        ) {
            Toast.makeText(requireContext(), R.string.t_noInternet, Toast.LENGTH_SHORT).show()
            return
        }
        lifecycleScope.launch { vm.billingManager.queryProductDetails() }
        findNavController().navigate(
            R.id.action_subscriptionsFragment_to_subscriptionsOverviewFragment,
            bundleOf(
                Pair(PurchasesOverviewFragment.Args.PLAN_NAME, getString(planNameRes)),
                Pair(PurchasesOverviewFragment.Args.PLAN_ID, purchase.ordinal)
            )
        )
    }

}