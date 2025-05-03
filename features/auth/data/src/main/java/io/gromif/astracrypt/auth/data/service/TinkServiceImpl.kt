package io.gromif.astracrypt.auth.data.service

import com.google.crypto.tink.prf.PrfSet
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.crypto.tink.core.extensions.prf
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.KeysetTemplates
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.keyset.associated_data.GetGlobalAssociatedDataPrf

class TinkServiceImpl(
    private val keysetManager: KeysetManager,
    private val associatedDataManager: AssociatedDataManager,
    private val getGlobalAssociatedDataPrf: GetGlobalAssociatedDataPrf
): TinkService {
    private var prfSetInterface: PrfSet? = null
    private suspend fun getPrfSet(): PrfSet {
        return prfSetInterface ?: getGlobalAssociatedDataPrf().also { prfSetInterface = it }
    }

    private var prfSetHashInterface: PrfSet? = null
    private suspend fun calculateHash(string: String, outputLength: Int): ByteArray {
        val prf = prfSetHashInterface ?: keysetManager.getKeyset(
            tag = KEYSET_TAG_HASH,
            associatedData = KEYSET_TAG_HASH_AD,
            keyParams = KeysetTemplates.PRF.HKDF_SHA256.params
        ).prf().also { prfSetHashInterface = it }
        return prf.computePrimary(string.toByteArray(), outputLength)
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

    override suspend fun decryptAssociatedData(password: String) {
        associatedDataManager.decryptWithPassword(
            password = password,
            prfSet = getPrfSet()
        )
    }

    override suspend fun computeAuthHash(data: String): ByteArray {
        return calculateHash(string = data, outputLength = 29)
    }

    override suspend fun computeSkinHash(data: String): ByteArray {
        return calculateHash(string = data, outputLength = 18)
    }

}

private const val KEYSET_TAG_HASH = "<-q<@1sN"
private val KEYSET_TAG_HASH_AD = "o@W5S)Q4".toByteArray()