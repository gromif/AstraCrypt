package com.nevidimka655.tink_lab.di

import android.net.Uri
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DtoToKeyMapper
import com.nevidimka655.tink_lab.data.repository.RepositoryImpl
import com.nevidimka655.tink_lab.data.util.KeyFactory
import com.nevidimka655.tink_lab.data.util.KeyWriter
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.model.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(
        keyFactory: KeyFactory,
        keyWriter: KeyWriter,
        dtoToKeyMapper: Mapper<KeyDto, Key>,
        stringToUriMapper: Mapper<String, Uri>
    ): Repository = RepositoryImpl(
        keyFactory = keyFactory,
        keyWriter = keyWriter,
        dtoToKeyMapper = dtoToKeyMapper,
        stringToUriMapper = stringToUriMapper
    )

}