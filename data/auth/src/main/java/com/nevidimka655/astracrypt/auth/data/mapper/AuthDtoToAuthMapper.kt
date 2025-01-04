package com.nevidimka655.astracrypt.auth.data.mapper

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.dto.SkinDto
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.auth.domain.AuthType
import com.nevidimka655.astracrypt.auth.domain.Skin
import com.nevidimka655.astracrypt.utils.Mapper

class AuthDtoToAuthMapper: Mapper<AuthDto, Auth> {
    override fun invoke(item: AuthDto): Auth {
        return Auth(
            type = AuthType.entries.getOrNull(item.type),
            skin = item.skin?.let {
                when (it) {
                    is SkinDto.Calculator -> Skin.Calculator(combinationHash = it.combinationHash)
                }
            },
            hintState = item.hintState,
            hintText = item.hintText,
            bindTinkAd = item.bindTinkAd
        )
    }
}