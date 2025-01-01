package com.nevidimka655.astracrypt.auth.data

import com.nevidimka655.astracrypt.auth.data.datastore.AuthDataStoreManager
import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.dto.SkinDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Repository
import com.nevidimka655.astracrypt.auth.domain.Skin
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.crypto.tink.core.encoders.Base64Util
import com.nevidimka655.crypto.tink.data.KeysetManager
import com.nevidimka655.crypto.tink.domain.KeysetTemplates
import com.nevidimka655.crypto.tink.extensions.prfPrimitive
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RepositoryImpl(
    private val keysetManager: KeysetManager,
    private val authDataStoreManager: AuthDataStoreManager,
    private val authToAuthDtoMapper: Mapper<Auth, AuthDto>,
    private val authDtoToAuthMapper: Mapper<AuthDto, Auth>,
    private val base64Util: Base64Util
) : Repository {

    private suspend fun calculateHash(string: String, outputLength: Int): ByteArray {
        val prf = keysetManager.getKeyset(
            tag = KEYSET_TAG_HASH,
            associatedData = KEYSET_TAG_HASH_AD,
            keyParams = KeysetTemplates.PRF.HMAC_SHA512_PRF.params
        ).prfPrimitive()
        return prf.computePrimary(string.toByteArray(), outputLength)
    }

    private suspend fun saveInfo(authDto: AuthDto) {
        authDataStoreManager.setAuthInfo(authDto = authDto)
    }

    override fun getAuthFlow(): Flow<Auth> {
        return authDataStoreManager.authFlow.map { authDtoToAuthMapper(it) }
    }

    override suspend fun setHintVisibility(auth: Auth, visible: Boolean) {
        val authDto = authToAuthDtoMapper(auth).copy(hintState = visible)
        saveInfo(authDto = authDto)
    }

    override suspend fun setHintText(auth: Auth, text: String) {
        val authDto = authToAuthDtoMapper(auth).copy(hintText = text)
        saveInfo(authDto = authDto)
    }


    override suspend fun setPassword(auth: Auth, password: String?): Unit = coroutineScope {
        launch {
            val hash = if (password != null) {
                calculateHash(string = password, outputLength = PASSWORD_HASH_LENGTH)
            } else null
            authDataStoreManager.setPasswordHash(hash = hash)
        }
        launch {
            val authDto = authToAuthDtoMapper(auth.copy(type = AuthType.PASSWORD))
            saveInfo(authDto = authDto)
        }
    }

    override suspend fun verifyPassword(password: String): Boolean = coroutineScope {
        val currentHash = async {
            calculateHash(string = password, outputLength = PASSWORD_HASH_LENGTH)
        }
        val savedHash = async { authDataStoreManager.getPasswordHash() }
        currentHash.await().contentEquals(savedHash.await())
    }

    override suspend fun disable(auth: Auth): Unit = coroutineScope {
        launch { authDataStoreManager.setPasswordHash(hash = null) }
        launch { saveInfo(authDto = authToAuthDtoMapper(auth).copy(type = -1)) }
    }

    private suspend fun computeCalculatorCombinationHash(combination: String): String {
        val combinationHash = calculateHash(
            string = combination,
            outputLength = CALCULATOR_COMBINATION_HASH_LENGTH
        )
        return base64Util.encode(combinationHash)
    }

    override suspend fun verifyCalculatorCombination(
        calculator: Skin.Calculator,
        combination: String
    ): Boolean {
        val combinationHash = computeCalculatorCombinationHash(combination = combination)
        return calculator.combinationHash.contentEquals(combinationHash)
    }

    override suspend fun setSkinCalculator(auth: Auth, combination: String) {
        val combinationHash = computeCalculatorCombinationHash(combination = combination)
        val skin = SkinDto.Calculator(combinationHash = combinationHash)
        val authDto = authToAuthDtoMapper(auth).copy(skin = skin)
        saveInfo(authDto = authDto)
    }

    override suspend fun setSkinDefault(auth: Auth) {
        val authDto = authToAuthDtoMapper(auth.copy(skin = null))
        saveInfo(authDto = authDto)
    }

}

private const val PASSWORD_HASH_LENGTH = 49
private const val CALCULATOR_COMBINATION_HASH_LENGTH = 20

private const val KEYSET_TAG_HASH = "<-q<@1sN"
private val KEYSET_TAG_HASH_AD = "o@W5S)Q4".toByteArray()