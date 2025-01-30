package com.nevidimka655.astracrypt.auth.di

import com.nevidimka655.astracrypt.auth.data.service.TinkServiceImpl
import com.nevidimka655.astracrypt.auth.domain.service.TinkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.crypto.tink.core.GetGlobalAssociatedDataPrf
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager

@Module
@InstallIn(ViewModelComponent::class)
internal object ServiceModule {

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

}