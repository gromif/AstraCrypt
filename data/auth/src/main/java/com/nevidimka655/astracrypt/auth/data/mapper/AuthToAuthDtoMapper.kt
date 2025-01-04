package com.nevidimka655.astracrypt.auth.data.mapper

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.dto.SkinDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Skin
import com.nevidimka655.astracrypt.utils.Mapper

class AuthToAuthDtoMapper: Mapper<Auth, AuthDto> {
    override fun invoke(item: Auth): AuthDto {
        return AuthDto(
            type = item.type?.ordinal ?: -1,
            skin = item.skin?.let {
                when (it) {
                    is Skin.Calculator -> SkinDto.Calculator(combinationHash = it.combinationHash)
                }
            },
            hintState = item.hintState,
            hintText = item.hintText,
            bindTinkAd = item.bindTinkAd
        )
    }
}