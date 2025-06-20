package io.gromif.tinkLab.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.tinkLab.data.repository.DefaultKeyRepository
import io.gromif.tinkLab.data.repository.RepositoryImpl
import io.gromif.tinkLab.data.util.KeyGenerator
import io.gromif.tinkLab.data.util.KeyReader
import io.gromif.tinkLab.data.util.KeyWriter
import io.gromif.tinkLab.domain.model.Repository
import io.gromif.tinkLab.domain.repository.KeyRepository

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideRepository(): Repository = RepositoryImpl()

    @ViewModelScoped
    @Provides
    fun provideKeyRepository(
        keyGenerator: KeyGenerator,
        keyWriter: KeyWriter,
        keyReader: KeyReader,
    ): KeyRepository = DefaultKeyRepository(
        keyGenerator = keyGenerator,
        keyWriter = keyWriter,
        keyReader = keyReader,
    )
}
