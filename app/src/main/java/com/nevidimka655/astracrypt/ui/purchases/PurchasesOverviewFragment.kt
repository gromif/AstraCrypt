package com.nevidimka655.astracrypt.ui.purchases

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.android.billingclient.api.ProductDetails
import com.nevidimka655.astracrypt.MainVM
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.ui.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.utils.EthernetTools
import com.nevidimka655.astracrypt.utils.billing.PlayBillingManager
import com.nevidimka655.astracrypt.utils.billing.PurchaseManager
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces
import kotlinx.coroutines.launch

class PurchasesOverviewFragment : Fragment() {
    private val vm by activityViewModels<MainVM>()
    private val args by navArgs<PurchasesOverviewFragmentArgs>()
    private val purchase by lazy { PurchaseManager.Purchase.entries[args.planCardId] }

    object Args {
        const val PLAN_NAME = "planName"
        const val PLAN_ID = "planCardId"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            AstraCryptTheme {
                PurchasesOverviewScreen(
                    purchase = purchase
                )
            }
        }
    }

    @Preview
    @Composable
    fun PurchasesOverviewScreen(
        playBillingManager: PlayBillingManager = vm.billingManager,
        purchase: PurchaseManager.Purchase = PurchaseManager.Purchase.Basic
    ) {
        val widthSizeClass = LocalWindowWidth.current
        val privileges = rememberSaveable {
            when (purchase) {
                PurchaseManager.Purchase.Basic -> createStarterPrivilegesList()
                PurchaseManager.Purchase.Premium -> createExtremePrivilegesList()
            }
        }
        val isVerifying = vm.billingManager.isVerifying
        val columnsCount = remember { widthSizeClass.cellsCount() }
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(rememberNestedScrollInteropConnection()),
            columns = GridCells.Fixed(columnsCount),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spaces.spaceMedium),
            contentPadding = PaddingValues(MaterialTheme.spaces.spaceMedium)
        ) {
            item {
                when (purchase) {
                    PurchaseManager.Purchase.Basic -> PurchaseCard()
                    PurchaseManager.Purchase.Premium -> PurchasePremiumCard()
                }
            }
            if (isVerifying) item {
                VerifyingPurchase()
            }
            item { ManageSubscription(playBillingManager, purchase) }
            items(privileges) { Privilege(name = it) }
        }
    }

    @Composable
    fun VerifyingPurchase() = CardWithTitle(
        titleText = stringResource(id = R.string.verifying)
    ) {
        Text(
            text = stringResource(id = R.string.verifying_msg),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    @Composable
    fun ManageSubscription(
        playBillingManager: PlayBillingManager,
        currentPurchase: PurchaseManager.Purchase
    ) = CardWithTitle(titleText = stringResource(id = R.string.files_options_details)) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val offersState = playBillingManager.productDetailsList
        val productDetailsList = offersState?.let {
            indexPlansInfo(purchase = currentPurchase, it)
        }
        val isPurchased = true
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = if (isPurchased) stringResource(id = R.string.purchased)
            else productDetailsList?.let {
                if (it.isNotEmpty()) {
                    val prod = it[0]
                    prod.oneTimePurchaseOfferDetails?.formattedPrice
                } else stringResource(id = R.string.purchased)
            } ?: stringResource(id = R.string.t_noInternet),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = R.string.purchaseConditions),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        FilledTonalButton(
            modifier = Modifier.align(Alignment.End),
            enabled = productDetailsList != null,
            onClick = {
                productDetailsList?.let {
                    coroutineScope.launch {
                        subscriptionButtonClick(
                            context = context,
                            playBillingManager = playBillingManager,
                            isPurchased = isPurchased,
                            productDetails = it[0]
                        )
                    }
                }
            }
        ) {
            Text(
                modifier = Modifier.animateContentSize(),
                text = if (isPurchased) stringResource(id = android.R.string.cancel)
                else stringResource(id = R.string.purchase)
            )
        }
    }

    @Preview
    @Composable
    fun Privilege(name: String = "Test") {
        Row(
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spaces.spaceSmall,
                vertical = MaterialTheme.spaces.spaceExtraSmall
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = MaterialTheme.spaces.spaceSmall)
                    .size(36.dp),
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    private suspend fun subscriptionButtonClick(
        context: Context,
        playBillingManager: PlayBillingManager,
        isPurchased: Boolean,
        productDetails: ProductDetails? = null
    ) {
        when {
            isPurchased -> EthernetTools.openUri(
                context,
                url = "https://support.google.com/googleplay/answer/7018481"
            )

            else -> productDetails?.let {
                playBillingManager.sendBuyRequest(productDetails = it)
            }
        }
    }

    private fun indexPlansInfo(purchase: PurchaseManager.Purchase, productList: List<ProductDetails>) =
        when (purchase) {
            PurchaseManager.Purchase.Basic -> listOfNotNull()

            PurchaseManager.Purchase.Premium -> listOfNotNull(
                productList.find {
                    it.productId == PlayBillingManager.INAPP.PRO_VERSION.id
                }
            ).ifEmpty { null }
        }

    private fun createStarterPrivilegesList() = listOf(
        getString(R.string.privilege_basicFeatures),
        "AES128 (EAX, CTR_HMAC_SHA256)"
    )
    private fun createExtremePrivilegesList() = listOf(
        getString(R.string.privilege_noAds),
        getString(R.string.privilege_unlimitedStorage),
        getString(R.string.privilege_dbEncryption),
        getString(R.string.privilege_authBindEncryption),
        getString(R.string.privilege_customCamouflages),
        getString(R.string.privilege_filesEncryptionInLab),
        getString(R.string.privilege_thumbEncryption),
        getString(R.string.settings_quickActions),
        getString(R.string.notes),
        "AES 128/256 + (GCM, EAX, CTR_HMAC_SHA256)",
        "ChaCha20 & XChaCha20 (Poly1305)"
    )
}