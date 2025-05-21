package io.gromif.astracrypt.files.data.util.parser

import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.utils.Parser
import kotlinx.serialization.json.Json

class ItemFlagsDtoParser : Parser<String, FileFlagsDto> {
    override fun invoke(item: String): FileFlagsDto {
        return Json.Default.decodeFromString(item)
    }
}
