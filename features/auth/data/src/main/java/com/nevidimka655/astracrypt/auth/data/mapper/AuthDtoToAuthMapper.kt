package com.nevidimka655.astracrypt.auth.data.mapper

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.auth.domain.model.AuthType
import com.nevidimka655.astracrypt.auth.domain.model.SkinType
import com.nevidimka655.astracrypt.utils.Mapper

class AuthDtoToAuthMapper: Mapper<AuthDto, Auth> {
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