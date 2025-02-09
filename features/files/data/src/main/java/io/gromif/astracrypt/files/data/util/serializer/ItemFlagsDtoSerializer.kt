package io.gromif.astracrypt.files.data.util.serializer

import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.utils.Serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ItemFlagsDtoSerializer : Serializer<FileFlagsDto, String> {
    override fun invoke(item: FileFlagsDto): String {
        return Json.Default.encodeToString(item)
    }
}