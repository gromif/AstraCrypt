package com.nevidimka655.tink_lab.di

import com.nevidimka655.tink_lab.data.repository.RepositoryImpl
import com.nevidimka655.tink_lab.domain.model.Repository
import com.nevidimka655.tink_lab.domain.util.KeyGenerator
import com.nevidimka655.tink_lab.domain.util.KeyReader
import com.nevidimka655.tink_lab.domain.util.KeyWriter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepository(
        keyGenerator: KeyGenerator,
        keyWriter: KeyWriter,
        keyReader: KeyReader
    ): Repository = RepositoryImpl(
        keyGenerator = keyGenerator,
        keyWriter = keyWriter,
        keyReader = keyReader
    )

}