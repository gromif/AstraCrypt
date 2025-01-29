package io.gromif.astracrypt.profile.data.mapper

import com.nevidimka655.astracrypt.utils.Mapper
import io.gromif.astracrypt.profile.data.dto.AvatarDto
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.domain.model.Avatar
import io.gromif.astracrypt.profile.domain.model.DefaultAvatar
import io.gromif.astracrypt.profile.domain.model.Profile

class ProfileMapper: Mapper<ProfileDto, Profile> {
    override fun invoke(item: ProfileDto): Profile {
        val icon = item.avatar
        return Profile(
            name = item.name,
            avatar = when(icon) {
                AvatarDto.External -> Avatar.External
                is AvatarDto.Default -> Avatar.Default(
                    defaultAvatar = DefaultAvatar.entries[icon.avatar.ordinal]
                )
            }
        )
    }
}