package com.nevidimka655.astracrypt.auth.data

import com.google.crypto.tink.prf.PrfSet
import com.nevidimka655.astracrypt.auth.domain.TinkRepository
import com.nevidimka655.crypto.tink.core.GetGlobalAssociatedDataPrf
import com.nevidimka655.crypto.tink.data.AssociatedDataManager

class TinkRepositoryImpl(
    private val associatedDataManager: AssociatedDataManager,
    private val getGlobalAssociatedDataPrf: GetGlobalAssociatedDataPrf
): TinkRepository {
    private var prfSetInterface: PrfSet? = null

    private suspend fun getPrfSet(): PrfSet {
        return prfSetInterface ?: getGlobalAssociatedDataPrf().also { prfSetInterface = it }
    }

    override suspend fun enableAssociatedDataBind(password: String) {
        associatedDataManager.encryptWithPassword(
            password = password,
            prfSet = getPrfSet()
        )
    }

    override suspend fun disableAssociatedDataBind() {
        associatedDataManager.decrypt()
    }

}