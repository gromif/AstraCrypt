package com.nevidimka655.astracrypt.auth.data.mapper

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.utils.Mapper

class AuthDtoMapper: Mapper<Auth, AuthDto> {
    override fun invoke(item: Auth): AuthDto {
        return AuthDto(
            type = item.type?.ordinal ?: -1,
            skin = item.skinType?.ordinal ?: -1,
            hintState = item.hintState,
            hintText = item.hintText,
            bindTinkAd = item.bindTinkAd
        )
    }
}