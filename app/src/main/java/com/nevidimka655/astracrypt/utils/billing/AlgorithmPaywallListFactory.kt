package com.nevidimka655.astracrypt.utils.billing

import com.nevidimka655.crypto.tink.KeysetTemplates

object AlgorithmPaywallListFactory {

    fun fetchItemsAead(
    ) = KeysetTemplates.AEAD.entries.mapIndexed { index, aead ->
        aead.name.lowercase()
    }.toMutableList()

    fun fetchItemsMinor() = KeysetTemplates.Stream.entries.filter {
        it.name.endsWith("KB")
    }.mapIndexed { index, streamingAEAD ->
        streamingAEAD.name.lowercase().removeSuffix("_4kb")
    }.toMutableList()

    fun fetchItemsMajor() = KeysetTemplates.Stream.entries.filter {
        it.name.endsWith("MB")
    }.mapIndexed { index, streamingAEAD ->
        streamingAEAD.name.lowercase().removeSuffix("_1mb")
    }.toMutableList()

}