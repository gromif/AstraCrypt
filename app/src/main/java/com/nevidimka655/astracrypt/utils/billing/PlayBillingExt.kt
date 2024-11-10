package com.nevidimka655.astracrypt.utils.billing

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase

fun Purchase.isPurchased() = this.purchaseState == Purchase.PurchaseState.PURCHASED
fun Purchase.isPending() = this.purchaseState == Purchase.PurchaseState.PENDING

fun BillingResult.isOk() = this.responseCode == BillingClient.BillingResponseCode.OK