package io.gromif.astracrypt.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.data.mapper.ProfileDtoMapper
import io.gromif.astracrypt.profile.data.mapper.ProfileMapper
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.utils.Mapper

@Module
@InstallIn(ViewModelComponent::class)
internal object MapperModule {

    @ViewModelScoped
    @Provides
    fun provideProfileInfoMapper(): Mapper<ProfileDto, Profile> = ProfileMapper()

    @ViewModelScoped
    @Provides
    fun provideProfileInfoDtoMapper(): Mapper<Profile, ProfileDto> = ProfileDtoMapper()

}