package io.gromif.astracrypt.profile.di

import android.content.Context
import coil3.ImageLoader
import coil3.request.CachePolicy
import coil3.request.transitionFactory
import coil3.transition.CrossfadeTransition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.data.util.FileUtil
import io.gromif.astracrypt.profile.data.util.TinkCoilFetcherFactory
import io.gromif.astracrypt.profile.domain.repository.SettingsRepository
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
internal object CoilModule {

    @AvatarImageLoader
    @ViewModelScoped
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        tinkCoilFetcherFactory: TinkCoilFetcherFactory
    ): ImageLoader = ImageLoader.Builder(context)
        .components {
            add(tinkCoilFetcherFactory)
        }
        .transitionFactory(CrossfadeTransition.Factory())
        .diskCachePolicy(CachePolicy.DISABLED)
        .build()

    @ViewModelScoped
    @Provides
    fun provideTinkCoilFetcherFactory(
        settingsRepository: SettingsRepository,
        fileUtil: FileUtil,
    ): TinkCoilFetcherFactory = TinkCoilFetcherFactory(
        settingsRepository = settingsRepository,
        fileUtil = fileUtil
    )
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AvatarImageLoader
