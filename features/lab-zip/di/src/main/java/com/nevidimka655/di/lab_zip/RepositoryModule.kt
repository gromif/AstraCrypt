package com.nevidimka655.di.lab_zip

import android.content.Context
import android.net.Uri
import com.nevidimka655.data.lab_zip.repository.RepositoryImpl
import com.nevidimka655.domain.lab_zip.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.gromif.astracrypt.utils.Mapper

@Module
@InstallIn(ViewModelComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideRepository(
        @ApplicationContext context: Context,
        stringToUriMapper: Mapper<String, Uri>
    ): Repository = RepositoryImpl(
        context = context,
        stringToUriMapper = stringToUriMapper
    )

}