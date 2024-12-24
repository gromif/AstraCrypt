package com.nevidimka655.tink_lab.data.util

import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KeySerializer(
    private val hexService: HexService
): Serializer<KeyDto, String> {
    override fun invoke(item: KeyDto): String {
        val keyDtoJson = Json.encodeToString<KeyDto>(item)
        return hexService.encode(keyDtoJson.toByteArray())
    }
}