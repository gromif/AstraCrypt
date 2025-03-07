package io.gromif.tink_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.prf.PrfSet
import io.gromif.crypto.tink.core.encoders.Encoder
import io.gromif.crypto.tink.data.KeysetManager
import io.gromif.crypto.tink.extensions.aead
import io.gromif.crypto.tink.extensions.decodeAndDecrypt
import io.gromif.crypto.tink.extensions.encryptAndEncode
import io.gromif.crypto.tink.extensions.prf
import io.gromif.crypto.tink.model.KeysetTemplates
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class TinkDataStore(
    private val dataStore: DataStore<Preferences>,
    private val keysetManager: KeysetManager,
    private val encoder: Encoder,
    private val params: Params,
) {

    data class Params(
        val keyPrfTemplate: KeysetTemplates.PRF = KeysetTemplates.PRF.HKDF_SHA256,
        val purpose: String,
        val keyTag: String = "ds_${purpose}_key",
        val keyAD: ByteArray? = "${keyTag}_ad".toByteArray(),
        val valueTag: String = "ds_${purpose}_value",
        val valueAD: ByteArray? = "${valueTag}_ad".toByteArray(),
        val keyPrfHashSize: Int = 24,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Params

            if (keyPrfHashSize != other.keyPrfHashSize) return false
            if (keyPrfTemplate != other.keyPrfTemplate) return false
            if (keyTag != other.keyTag) return false
            if (!keyAD.contentEquals(other.keyAD)) return false
            if (valueTag != other.valueTag) return false
            if (!valueAD.contentEquals(other.valueAD)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = keyPrfHashSize
            result = 31 * result + keyPrfTemplate.hashCode()
            result = 31 * result + keyTag.hashCode()
            result = 31 * result + keyAD.contentHashCode()
            result = 31 * result + valueTag.hashCode()
            result = 31 * result + valueAD.contentHashCode()
            return result
        }

    }

    private val mutex = Mutex()
    private var cachedKeyPrf: PrfSet? = null
    private var cachedValueAead: Aead? = null
    private val preferencesKeyList = mutableListOf<String>()
    private val preferencesKeyHashMap = hashMapOf<String, String>()


    private val aeadKey = intPreferencesKey(KEY_AEAD)
    private val aeadFlow = dataStore.data.map {
        val savedValue = it[aeadKey]
        if (savedValue == null) DEFAULT_AEAD else {
            KeysetTemplates.AEAD.entries.getOrNull(savedValue)
        }
    }
    private suspend fun setAeadTemplate(aead: KeysetTemplates.AEAD?) {
        dataStore.edit { it[aeadKey] = aead?.ordinal ?: -1 }
    }

    protected fun getTinkDataStoreAeadFlow(): Flow<KeysetTemplates.AEAD?> {
        return aeadFlow
    }

    protected suspend fun getTinkDataStoreAead(): KeysetTemplates.AEAD? {
        return aeadFlow.first()
    }


    private suspend fun getKeyPrf(): PrfSet = cachedKeyPrf ?: keysetManager.getKeyset(
        tag = params.keyTag,
        associatedData = params.keyAD,
        keyParams = params.keyPrfTemplate.params
    ).prf().also { cachedKeyPrf = it }

    private suspend fun getValueAead(aead: KeysetTemplates.AEAD): Aead {
        return cachedValueAead ?: keysetManager.getKeyset(
            tag = params.valueTag,
            associatedData = params.valueAD,
            keyParams = aead.params
        ).aead().also { cachedValueAead = it }
    }


    private suspend fun prfHashKeyWithBase64(key: String): String {
        return preferencesKeyHashMap[key] ?: run {
            val prf = getKeyPrf()
            val prfBytes = prf.computePrimary(key.toByteArray(), params.keyPrfHashSize)
            encoder.encode(prfBytes).also { preferencesKeyHashMap[key] = it }
        }
    }

    protected fun tinkPreference(name: String): Preferences.Key<String> {
        preferencesKeyList.add(name)
        return stringPreferencesKey(name)
    }

    protected suspend fun MutablePreferences.setData(key: String, value: String) {
        val aead = getTinkDataStoreAead()
        if (aead != null) {
            val keyPrfHash = prfHashKeyWithBase64(key)
            val associatedData = "${key}_${keyPrfHash}".toByteArray()
            val aead = getValueAead(aead)
            val encryptedData = aead.encryptAndEncode(value, associatedData, encoder)
            set(stringPreferencesKey(keyPrfHash), encryptedData)
        } else set(stringPreferencesKey(key), value)
    }

    protected suspend fun Preferences.getData(key: String): String? {
        val aead = getTinkDataStoreAead()
        return if (aead != null) {
            val keyPrfHash = prfHashKeyWithBase64(key)
            val data = get(stringPreferencesKey(keyPrfHash)) ?: return null
            val associatedData = "${key}_${keyPrfHash}".toByteArray()
            val aead = getValueAead(aead)
            aead.decodeAndDecrypt(data, associatedData, encoder)
        } else get(stringPreferencesKey(key))
    }


    protected suspend fun setTinkDataStoreAead(
        targetAead: KeysetTemplates.AEAD?,
    ): Unit = mutex.withLock {
        val tempPrefsMap = hashMapOf<String, String?>()
        dataStore.edit { prefs ->
            preferencesKeyList.forEach { currentKey ->
                val data = prefs.getData(currentKey)
                tempPrefsMap[currentKey] = data
                prefs.remove(stringPreferencesKey(currentKey))
            }
        }

        setAeadTemplate(targetAead)
        resetCachedAead()

        dataStore.edit { prefs ->
            preferencesKeyList.forEach { currentKey ->
                val data = tempPrefsMap[currentKey] ?: return@forEach
                prefs.setData(currentKey, data)
            }
        }
    }

    private fun resetCachedAead() {
        cachedKeyPrf = null
        cachedValueAead = null
    }

}

private const val KEY_AEAD = "aead"

private val DEFAULT_AEAD = KeysetTemplates.AEAD.AES128_EAX