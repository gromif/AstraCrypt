package com.nevidimka655.astracrypt.features.details

import com.nevidimka655.compose_details.DetailsManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DetailsManagerModule {

    @Provides
    fun provideDetailsManager() = DetailsManager()

}