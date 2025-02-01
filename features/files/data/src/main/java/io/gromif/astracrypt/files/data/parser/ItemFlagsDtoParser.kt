package io.gromif.astracrypt.files.data.parser

import com.nevidimka655.astracrypt.utils.Parser
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import kotlinx.serialization.json.Json

class ItemFlagsDtoParser : Parser<String, FileFlagsDto> {
    override fun invoke(item: String): FileFlagsDto {
        return Json.decodeFromString(item)
    }
}