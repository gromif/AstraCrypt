package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.dto.AuthDto
import com.nevidimka655.astracrypt.auth.data.mapper.AuthDtoToAuthMapper
import com.nevidimka655.astracrypt.auth.data.mapper.AuthToAuthDtoMapper
import com.nevidimka655.astracrypt.auth.domain.Auth
import com.nevidimka655.astracrypt.utils.Mapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object MapperModule {

    @Provides
    fun provideAuthToAuthDtoMapper(): Mapper<Auth, AuthDto> = AuthToAuthDtoMapper()

    @Provides
    fun provideAuthDtoToAuthMapper(): Mapper<AuthDto, Auth> = AuthDtoToAuthMapper()

}