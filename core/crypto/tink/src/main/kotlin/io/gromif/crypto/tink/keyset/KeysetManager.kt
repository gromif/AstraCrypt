package io.gromif.crypto.tink.keyset

import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.Parameters
import com.google.crypto.tink.config.TinkConfig
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.kms.KeyManagementService
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Utility for low-level keyset management.
 */
class KeysetManager(
    private val keyManagementService: KeyManagementService,
    private val associatedDataManager: AssociatedDataManager
) {
    private val mutex = Mutex()
    private val keysetList = HashMap<Int, KeysetHandle>()

    suspend fun getKeyset(
        tag: String,
        associatedData: ByteArray? = null,
        keyParams: Parameters,
        keysetTag: String = "$tag:$keyParams",
        keysetTagHash: Int = keysetTag.hashCode(),
        cache: Boolean = true
    ): KeysetHandle = mutex.withLock {
        val ad = associatedData ?: associatedDataManager.getAssociatedData()

        keysetList[keysetTagHash] ?: keyManagementService.create(
            tag = keysetTag,
            keyParams = keyParams,
            associatedData = ad
        ).also { if (cache) keysetList[keysetTagHash] = it }
    }

    init {
        TinkConfig.register()
    }

}