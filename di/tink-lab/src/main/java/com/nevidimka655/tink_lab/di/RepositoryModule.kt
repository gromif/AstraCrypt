package com.nevidimka655.tink_lab.di

import com.nevidimka655.tink_lab.data.repository.RepositoryImpl
import com.nevidimka655.tink_lab.domain.model.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRepository(): Repository = RepositoryImpl()

}