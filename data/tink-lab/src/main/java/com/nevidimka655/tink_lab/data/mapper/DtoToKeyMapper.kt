package com.nevidimka655.tink_lab.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.domain.model.Key

class DtoToKeyMapper: Mapper<KeyDto, Key> {
    override fun invoke(item: KeyDto): Key = item.run {
        Key(
            dataType = dataType,
            aeadType = aeadType,
            encryptedKeyset = encryptedKeyset,
            hash = hash
        )
    }
}