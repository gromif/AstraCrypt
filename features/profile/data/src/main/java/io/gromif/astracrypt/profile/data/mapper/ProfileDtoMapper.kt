package io.gromif.astracrypt.profile.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.profile.data.dto.AvatarDto
import io.gromif.astracrypt.profile.data.dto.DefaultAvatarDto
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.Profile

class ProfileDtoMapper: Mapper<Profile, ProfileDto> {
    override fun invoke(item: Profile): ProfileDto {
        val icon = item.avatar
        return ProfileDto(
            name = item.name,
            avatar = when(icon) {
                Avatar.External -> AvatarDto.External
                is Avatar.Default -> AvatarDto.Default(
                    avatar = DefaultAvatarDto.entries[icon.defaultAvatar.ordinal]
                )
            }
        )
    }
}