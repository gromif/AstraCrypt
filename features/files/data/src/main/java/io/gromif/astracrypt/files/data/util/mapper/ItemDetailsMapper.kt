package io.gromif.astracrypt.files.data.util.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.Parser
import io.gromif.astracrypt.files.data.db.tuples.DetailsTuple
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.domain.model.FileFlags
import io.gromif.astracrypt.files.domain.model.FileSource
import io.gromif.astracrypt.files.domain.model.ItemDetails

class ItemDetailsMapper(
    private val itemFlagsMapper: Mapper<FileFlagsDto, FileFlags>,
    private val itemFlagsDtoParser: Parser<String, FileFlagsDto>,
) : Mapper<DetailsTuple, ItemDetails> {
    override fun invoke(item: DetailsTuple): ItemDetails {
        val flags = item.flags?.let {
            val dto = itemFlagsDtoParser(it)
            itemFlagsMapper(dto)
        }
        val file = FileSource(item.file!!, item.fileAead)
        val preview = item.preview?.let {
            FileSource(it, item.fileAead)
        }
        return ItemDetails.File(
            name = item.name,
            type = item.type,
            file = file,
            preview = preview,
            flags = flags,
            size = item.size,
            creationTime = item.creationTime
        )
    }
}