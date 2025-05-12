package io.gromif.crypto.tink.aead

import com.google.crypto.tink.Aead
import com.google.crypto.tink.DeterministicAead
import com.google.crypto.tink.StreamingAead
import com.google.crypto.tink.aead.AeadParameters
import com.google.crypto.tink.daead.DeterministicAeadParameters
import com.google.crypto.tink.prf.PrfParameters
import com.google.crypto.tink.prf.PrfSet
import com.google.crypto.tink.streamingaead.StreamingAeadParameters
import io.gromif.crypto.tink.core.extensions.aead
import io.gromif.crypto.tink.core.extensions.deterministicAead
import io.gromif.crypto.tink.core.extensions.prf
import io.gromif.crypto.tink.core.extensions.streamingAead
import io.gromif.crypto.tink.keyset.KeysetManager

class AeadManager(
    private val keysetManager: KeysetManager
) {
    private val aeadMap = HashMap<Int, Aead>()
    private val prfMap = HashMap<Int, PrfSet>()
    private val deterministicAeadMap = HashMap<Int, DeterministicAead>()
    private val streamingAeadMap = HashMap<Int, StreamingAead>()

    suspend fun aead(
        tag: String,
        associatedData: ByteArray? = null,
        keyParams: AeadParameters,
    ): Aead {
        val keysetTag = "$tag:$keyParams"
        val keysetTagHash = keysetTag.hashCode()
        return aeadMap[keysetTagHash] ?: keysetManager.getKeyset(
            tag = tag,
            keysetTag = keysetTag,
            keysetTagHash = keysetTagHash,
            associatedData = associatedData,
            keyParams = keyParams,
            cache = false
        ).aead().also {
            aeadMap[keysetTagHash] = it
        }
    }

    suspend fun prf(
        tag: String,
        associatedData: ByteArray? = null,
        keyParams: PrfParameters,
    ): PrfSet {
        val keysetTag = "$tag:$keyParams"
        val keysetTagHash = keysetTag.hashCode()
        return prfMap[keysetTagHash] ?: keysetManager.getKeyset(
            tag = tag,
            keysetTag = keysetTag,
            keysetTagHash = keysetTagHash,
            associatedData = associatedData,
            keyParams = keyParams,
            cache = false
        ).prf().also {
            prfMap[keysetTagHash] = it
        }
    }

    suspend fun deterministicAead(
        tag: String,
        associatedData: ByteArray? = null,
        keyParams: DeterministicAeadParameters,
    ): DeterministicAead {
        val keysetTag = "$tag:$keyParams"
        val keysetTagHash = keysetTag.hashCode()
        return deterministicAeadMap[keysetTagHash] ?: keysetManager.getKeyset(
            tag = tag,
            keysetTag = keysetTag,
            keysetTagHash = keysetTagHash,
            associatedData = associatedData,
            keyParams = keyParams,
            cache = false
        ).deterministicAead().also {
            deterministicAeadMap[keysetTagHash] = it
        }
    }

    suspend fun streamingAead(
        tag: String,
        associatedData: ByteArray? = null,
        keyParams: StreamingAeadParameters,
    ): StreamingAead {
        val keysetTag = "$tag:$keyParams"
        val keysetTagHash = keysetTag.hashCode()
        return streamingAeadMap[keysetTagHash] ?: keysetManager.getKeyset(
            tag = tag,
            keysetTag = keysetTag,
            keysetTagHash = keysetTagHash,
            associatedData = associatedData,
            keyParams = keyParams,
            cache = false
        ).streamingAead().also {
            streamingAeadMap[keysetTagHash] = it
        }
    }

}