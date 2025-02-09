package io.gromif.astracrypt.auth.data.data.mapper

import io.gromif.astracrypt.auth.data.data.dto.AuthDto
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.auth.domain.model.AuthType
import io.gromif.astracrypt.auth.domain.model.SkinType
import io.gromif.astracrypt.utils.Mapper

class AuthMapper: Mapper<AuthDto, Auth> {
    override fun invoke(item: AuthDto): Auth {
        return Auth(
            type = AuthType.entries.getOrNull(item.type),
            skinType = SkinType.entries.getOrNull(item.skin),
            hintState = item.hintState,
            hintText = item.hintText,
            bindTinkAd = item.bindTinkAd
        )
    }
}