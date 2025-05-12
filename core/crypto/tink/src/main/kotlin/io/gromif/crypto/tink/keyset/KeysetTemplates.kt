package io.gromif.crypto.tink.keyset

import com.google.crypto.tink.aead.AeadParameters
import com.google.crypto.tink.aead.PredefinedAeadParameters
import com.google.crypto.tink.daead.DeterministicAeadParameters
import com.google.crypto.tink.daead.PredefinedDeterministicAeadParameters
import com.google.crypto.tink.prf.PredefinedPrfParameters
import com.google.crypto.tink.prf.PrfParameters
import com.google.crypto.tink.streamingaead.PredefinedStreamingAeadParameters
import com.google.crypto.tink.streamingaead.StreamingAeadParameters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private typealias AeadParams = PredefinedAeadParameters
private typealias DeterministicParams = PredefinedDeterministicAeadParameters
private typealias StreamingParams = PredefinedStreamingAeadParameters
private typealias PrfParams = PredefinedPrfParameters

object KeysetTemplates {

    @Serializable
    enum class AEAD(val params: AeadParameters) {
        @SerialName("a") AES128_EAX(AeadParams.AES128_EAX),
        @SerialName("b") AES256_EAX(AeadParams.AES256_EAX),

        @SerialName("c") AES128_GCM(AeadParams.AES128_GCM),
        @SerialName("d") AES256_GCM(AeadParams.AES256_GCM),

        @SerialName("e") AES128_CTR_HMAC_SHA256(AeadParams.AES128_CTR_HMAC_SHA256),
        @SerialName("f") AES256_CTR_HMAC_SHA256(AeadParams.AES256_CTR_HMAC_SHA256),

        @SerialName("g") CHACHA20_POLY1305(AeadParams.CHACHA20_POLY1305),
        @SerialName("h") XCHACHA20_POLY1305(AeadParams.XCHACHA20_POLY1305),

        @SerialName("i") XAES_256_GCM_160_BIT_NONCE_NO_PREFIX(AeadParams.XAES_256_GCM_160_BIT_NONCE_NO_PREFIX),
        @SerialName("j") XAES_256_GCM_192_BIT_NONCE(AeadParams.XAES_256_GCM_192_BIT_NONCE),
        @SerialName("k") XAES_256_GCM_192_BIT_NONCE_NO_PREFIX(AeadParams.XAES_256_GCM_192_BIT_NONCE_NO_PREFIX)
    }

    @Serializable
    enum class DeterministicAEAD(val params: DeterministicAeadParameters) {
        @SerialName("a") AES256_SIV(DeterministicParams.AES256_SIV)
    }

    @Serializable
    enum class Stream(val params: StreamingAeadParameters) {
        @SerialName("a") AES128_CTR_HMAC_SHA256_1MB(StreamingParams.AES128_CTR_HMAC_SHA256_1MB),
        @SerialName("b") AES128_CTR_HMAC_SHA256_4KB(StreamingParams.AES128_CTR_HMAC_SHA256_4KB),

        @SerialName("c") AES128_GCM_HKDF_1MB(StreamingParams.AES128_GCM_HKDF_1MB),
        @SerialName("d") AES128_GCM_HKDF_4KB(StreamingParams.AES128_GCM_HKDF_4KB),

        @SerialName("e") AES256_CTR_HMAC_SHA256_1MB(StreamingParams.AES256_CTR_HMAC_SHA256_1MB),
        @SerialName("f") AES256_CTR_HMAC_SHA256_4KB(StreamingParams.AES256_CTR_HMAC_SHA256_4KB),

        @SerialName("g") AES256_GCM_HKDF_1MB(StreamingParams.AES256_GCM_HKDF_1MB),
        @SerialName("h") AES256_GCM_HKDF_4KB(StreamingParams.AES256_GCM_HKDF_4KB)
    }

    @Serializable
    enum class PRF(val params: PrfParameters) {
        @SerialName("a") HKDF_SHA256(PrfParams.HKDF_SHA256),
        @SerialName("b") HMAC_SHA256_PRF(PrfParams.HMAC_SHA256_PRF),
        @SerialName("c") HMAC_SHA512_PRF(PrfParams.HMAC_SHA512_PRF),
        @SerialName("d") AES_CMAC_PRF(PrfParams.AES_CMAC_PRF),
    }

}