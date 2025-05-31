package io.gromif.astracrypt.files.di.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.files.data.repository.DefaultStorageNavigator
import io.gromif.astracrypt.files.domain.repository.StorageNavigator

@Module
@InstallIn(ViewModelComponent::class)
internal object StorageNavigatorModule {

    @ViewModelScoped
    @Provides
    fun provideStorageNavigator(): StorageNavigator = DefaultStorageNavigator()
}
