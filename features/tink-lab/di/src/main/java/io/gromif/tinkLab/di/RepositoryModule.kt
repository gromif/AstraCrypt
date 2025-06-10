package io.gromif.tinkLab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.tinkLab.data.repository.RepositoryImpl
import io.gromif.tinkLab.domain.model.Repository
import io.gromif.tinkLab.domain.util.KeyGenerator
import io.gromif.tinkLab.domain.util.KeyReader
import io.gromif.tinkLab.domain.util.KeyWriter

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(
        keyGenerator: KeyGenerator,
        keyWriter: KeyWriter,
        keyReader: KeyReader,
    ): Repository = RepositoryImpl(
        keyGenerator = keyGenerator,
        keyWriter = keyWriter,
        keyReader = keyReader,
    )

}