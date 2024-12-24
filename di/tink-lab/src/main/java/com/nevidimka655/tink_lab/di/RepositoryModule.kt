package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.repository.RepositoryImpl
import com.nevidimka655.tink_lab.data.util.KeyFactory
import com.nevidimka655.tink_lab.domain.model.Key
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.util.KeyWriter
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
        dtoToKeyMapper: Mapper<KeyDto, Key>
    ): Repository = RepositoryImpl(
        keyFactory = keyFactory,
        keyWriter = keyWriter,
        dtoToKeyMapper = dtoToKeyMapper
    )

}