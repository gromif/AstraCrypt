package com.nevidimka655.tink_lab.di

import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.tink_lab.data.dto.KeyDto
import com.nevidimka655.tink_lab.data.mapper.DtoToKeyMapper
import com.nevidimka655.tink_lab.data.repository.RepositoryImpl
import com.nevidimka655.tink_lab.data.util.KeyFactory
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
        dtoToKeyMapper: Mapper<KeyDto, Key>
    ): Repository = RepositoryImpl(
        keyFactory = keyFactory,
        dtoToKeyMapper = dtoToKeyMapper
    )

}