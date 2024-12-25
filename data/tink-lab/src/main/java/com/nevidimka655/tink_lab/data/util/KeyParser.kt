package com.nevidimka655.tink_lab.data.util

import com.nevidimka655.astracrypt.utils.Parser
import com.nevidimka655.astracrypt.utils.Serializer
import com.nevidimka655.crypto.tink.core.encoders.HexService
import com.nevidimka655.tink_lab.data.dto.KeyDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KeyParser(
    private val hexService: HexService
): Parser<String, KeyDto> {
    override fun invoke(item: String): KeyDto {
        val json = hexService.decode(item).decodeToString()
        return Json.decodeFromString(json)
    }
}