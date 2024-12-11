package com.nevidimka655.astracrypt.view.ui.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.app.utils.billing.PurchaseManager
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces

class PurchasesFragment : Fragment() {

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
        val cellsCount = LocalWindowWidth.current.cellsCount()
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
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
                PurchasePremiumCard(isPurchased = true) { planNameRes ->
                    openPlan(planNameRes, PurchaseManager.Purchase.Premium)
                }
            }
        }
    }

    private fun openPlan(planNameRes: Int, purchase: PurchaseManager.Purchase) {
        findNavController().navigate(
            R.id.action_subscriptionsFragment_to_subscriptionsOverviewFragment,
            bundleOf(
                Pair(PurchasesOverviewFragment.Args.PLAN_NAME, getString(planNameRes)),
                Pair(PurchasesOverviewFragment.Args.PLAN_ID, purchase.ordinal)
            )
        )
    }

}