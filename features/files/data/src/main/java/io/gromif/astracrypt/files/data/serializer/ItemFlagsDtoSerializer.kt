package io.gromif.astracrypt.files.data.serializer

import com.nevidimka655.astracrypt.utils.Serializer
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ItemFlagsDtoSerializer : Serializer<FileFlagsDto, String> {
    override fun invoke(item: FileFlagsDto): String {
        return Json.encodeToString(item)
    }
}