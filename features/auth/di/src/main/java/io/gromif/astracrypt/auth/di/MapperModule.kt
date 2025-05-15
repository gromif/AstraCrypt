package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.data.mapper.AuthDtoMapper
import io.gromif.astracrypt.auth.data.mapper.AuthMapper
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.utils.Mapper

@Module
@InstallIn(SingletonComponent::class)
internal object MapperModule {

    @Provides
    fun provideAuthDtoMapper(): Mapper<Auth, AuthDto> = AuthDtoMapper()

    @Provides
    fun provideAuthMapper(): Mapper<AuthDto, Auth> = AuthMapper()
}
