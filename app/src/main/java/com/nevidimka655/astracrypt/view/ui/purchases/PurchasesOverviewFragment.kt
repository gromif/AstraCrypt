package com.nevidimka655.astracrypt.view.ui.purchases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.nevidimka655.astracrypt.R
import com.nevidimka655.astracrypt.app.theme.AstraCryptTheme
import com.nevidimka655.astracrypt.app.utils.billing.PurchaseManager
import com.nevidimka655.ui.compose_core.CardWithTitle
import com.nevidimka655.ui.compose_core.ext.LocalWindowWidth
import com.nevidimka655.ui.compose_core.ext.cellsCount
import com.nevidimka655.ui.compose_core.theme.spaces

class PurchasesOverviewFragment : Fragment() {
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
                PurchasesOverviewScreen(purchase = purchase)
            }
        }
    }

    @Preview
    @Composable
    fun PurchasesOverviewScreen(
        purchase: PurchaseManager.Purchase = PurchaseManager.Purchase.Basic
    ) {
        val widthSizeClass = LocalWindowWidth.current
        val privileges = rememberSaveable {
            when (purchase) {
                PurchaseManager.Purchase.Basic -> createStarterPrivilegesList()
                PurchaseManager.Purchase.Premium -> createExtremePrivilegesList()
            }
        }
        val columnsCount = remember { widthSizeClass.cellsCount() }
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
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
            item { ManageSubscription() }
            items(privileges) { Privilege(name = it) }
        }
    }

    @Composable
    fun ManageSubscription(
    ) = CardWithTitle(titleText = stringResource(id = R.string.files_options_details)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.purchased),
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