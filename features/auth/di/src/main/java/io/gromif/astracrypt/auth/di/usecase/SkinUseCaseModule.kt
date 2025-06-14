package io.gromif.astracrypt.auth.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.SetSkinTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.skin.VerifySkinUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object SkinUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideSetSkinCalculatorUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ) = SetSkinTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkService = tinkService
    )

    @ViewModelScoped
    @Provides
    fun provideVerifyCalculatorCombinationUseCase(
        settingsRepository: SettingsRepository,
        repository: Repository,
    ) = VerifySkinUseCase(settingsRepository = settingsRepository, repository = repository)

}
