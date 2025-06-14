package io.gromif.tinkLab.data.util

import io.gromif.astracrypt.utils.Parser
import io.gromif.crypto.tink.core.encoders.HexEncoder
import io.gromif.tinkLab.data.dto.KeyDto
import kotlinx.serialization.json.Json

class KeyParser(
    private val hexEncoder: HexEncoder
) : Parser<String, KeyDto> {
    override fun invoke(item: String): KeyDto {
        val json = hexEncoder.decode(item).decodeToString()
        return Json.decodeFromString(json)
    }
}
