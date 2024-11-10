package com.nevidimka655.astracrypt.utils.billing

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.nevidimka655.astracrypt.utils.Engine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class PlayBillingManager(private val viewModelScope: CoroutineScope) : PurchasesUpdatedListener {
    private val billingClient = buildBillingClient()

    val buyRequestParams: Channel<BillingFlowParams?> = Channel()

    private val _onPurchaseMutableStateFlow = MutableStateFlow<PurchaseInfo?>(null)

    var productDetailsList by mutableStateOf<List<ProductDetails>?>(null)
    var isVerifying by mutableStateOf(false)

    companion object {
        fun isServicesAvailable(context: Context) = GoogleApiAvailabilityLight
            .getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }

    enum class INAPP(val id: String) {
        PRO_VERSION("pro_version")
    }

    data class PurchaseInfo(
        val purchase: Purchase? = null
    )

    suspend fun queryProductDetails() {
        val productList = INAPP.entries.map {
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(it.id)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }
        val params = QueryProductDetailsParams.newBuilder().setProductList(productList).build()
        productDetailsList = billingClient.queryProductDetails(params).productDetailsList
    }

    fun launchBillingFlow(activity: Activity, billingParams: BillingFlowParams) {
        billingClient.launchBillingFlow(activity, billingParams)
    }

    suspend fun sendBuyRequest(productDetails: ProductDetails) {
        val productDetailsParams = BillingFlowParams.ProductDetailsParams.newBuilder()
            .setProductDetails(productDetails)
            .build()
        val billingParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(listOf(productDetailsParams))
            .build()
        buyRequestParams.send(billingParams)
    }

    private fun buildBillingClient() = BillingClient
        .newBuilder(Engine.appContext)
        .setListener(this)
        .enablePendingPurchases()
        .build()

    override fun onPurchasesUpdated(
        billingResult: BillingResult,
        purchases: MutableList<Purchase>?
    ) {
        if (!billingResult.isOk()) return
        val purchaseItem = purchases?.firstOrNull()
        if (purchaseItem != null) viewModelScope.launch {
            isVerifying = true
            val purchaseInfo = PurchaseInfo(purchase = purchaseItem)
            _onPurchaseMutableStateFlow.update { purchaseInfo }
            //consume(purchases!![0].purchaseToken)
            handlePurchase(purchases)
            isVerifying = false
        }
    }

    private suspend fun handlePurchase(purchases: MutableList<Purchase>?) = purchases?.forEach {
        if (it.isPurchased() && !it.isAcknowledged) {
            val acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(it.purchaseToken)
                .build()
            billingClient.acknowledgePurchase(acknowledgePurchaseParams)
        }
    }

}