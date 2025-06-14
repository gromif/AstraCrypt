package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.auth.data.service.ClockServiceImpl
import io.gromif.astracrypt.auth.data.service.TinkServiceImpl
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.keyset.associated_data.GetGlobalAssociatedDataPrf
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Singleton
    @Provides
    fun provideTinkService(
        keysetManager: KeysetManager,
        associatedDataManager: AssociatedDataManager,
        getGlobalAssociatedDataPrf: GetGlobalAssociatedDataPrf
    ): TinkService = TinkServiceImpl(
        keysetManager = keysetManager,
        associatedDataManager = associatedDataManager,
        getGlobalAssociatedDataPrf = getGlobalAssociatedDataPrf
    )

    @Singleton
    @Provides
    fun provideClockService(): ClockService = ClockServiceImpl()
}
