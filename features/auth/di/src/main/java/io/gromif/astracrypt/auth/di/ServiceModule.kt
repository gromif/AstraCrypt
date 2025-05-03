package io.gromif.astracrypt.auth.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.data.service.ClockServiceImpl
import io.gromif.astracrypt.auth.data.service.TinkServiceImpl
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.crypto.tink.keyset.KeysetManager
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import io.gromif.crypto.tink.keyset.associated_data.GetGlobalAssociatedDataPrf

@Module
@InstallIn(ViewModelComponent::class)
internal object ServiceModule {

    @ViewModelScoped
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

    @ViewModelScoped
    @Provides
    fun provideClockService(): ClockService = ClockServiceImpl()

}