package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.mapper.AuthDtoMapper
import com.nevidimka655.astracrypt.auth.data.mapper.AuthMapper
import com.nevidimka655.astracrypt.auth.domain.model.Auth
import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

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