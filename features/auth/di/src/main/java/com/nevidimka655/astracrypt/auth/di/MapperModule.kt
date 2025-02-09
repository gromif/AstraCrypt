package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.mapper.AuthDtoMapper
import com.nevidimka655.astracrypt.auth.data.mapper.AuthMapper
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
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