package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.data.dto.AuthDto
import io.gromif.astracrypt.auth.data.mapper.AuthDtoMapper
import io.gromif.astracrypt.auth.data.mapper.AuthMapper
import io.gromif.astracrypt.auth.domain.model.Auth
import io.gromif.astracrypt.utils.Mapper

@Module
@InstallIn(ViewModelComponent::class)
internal object MapperModule {

    @ViewModelScoped
    @Provides
    fun provideAuthDtoMapper(): Mapper<Auth, AuthDto> = AuthDtoMapper()

    @ViewModelScoped
    @Provides
    fun provideAuthMapper(): Mapper<AuthDto, Auth> = AuthMapper()

}