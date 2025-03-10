package io.gromif.astracrypt.profile.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.profile.data.dto.ProfileDto
import io.gromif.astracrypt.profile.data.mapper.ProfileDtoMapper
import io.gromif.astracrypt.profile.data.mapper.ProfileMapper
import io.gromif.astracrypt.profile.domain.model.Profile
import io.gromif.astracrypt.utils.Mapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Singleton
    @Provides
    fun provideProfileInfoMapper(): Mapper<ProfileDto, Profile> = ProfileMapper()

    @Singleton
    @Provides
    fun provideProfileInfoDtoMapper(): Mapper<Profile, ProfileDto> = ProfileDtoMapper()

}