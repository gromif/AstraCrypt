package io.gromif.astracrypt.auth.data.mapper

import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.utils.Mapper

class AuthDtoMapper: Mapper<Auth, AuthDto> {
    override fun invoke(item: Auth): AuthDto {
        return AuthDto(
            type = item.type?.ordinal ?: -1,
            timeout = item.timeout.ordinal,
            skin = item.skinType?.ordinal ?: -1,
            hintState = item.hintState,
            hintText = item.hintText,
            bindTinkAd = item.bindTinkAd
        )
    }
}