package io.gromif.astracrypt.settings.aead.di

import com.nevidimka655.domain.notes.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.settings.aead.data.repository.RepositoryImpl
import io.gromif.astracrypt.settings.aead.domain.repository.Repository

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideRepository(
        notesSettingsRepository: NotesSettingsRepository
    ): Repository = RepositoryImpl(
        notesSettingsRepository = notesSettingsRepository
    )

}

typealias NotesSettingsRepository = SettingsRepository