package io.gromif.lab_zip.di

import android.content.Context
import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.gromif.astracrypt.utils.Mapper
import io.gromif.lab_zip.data.RepositoryImpl
import io.gromif.lab_zip.domain.Repository

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