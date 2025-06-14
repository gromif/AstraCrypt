package io.gromif.astracrypt.auth.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.repository.SettingsRepository
import io.gromif.astracrypt.auth.domain.service.TinkService
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthTypeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.VerifyAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object AuthUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetAuthUseCase(settingsRepository: SettingsRepository) =
        GetAuthUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAuthUseCase(settingsRepository: SettingsRepository) =
        SetAuthUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetAuthFlowUseCase(settingsRepository: SettingsRepository) =
        GetAuthFlowUseCase(settingsRepository = settingsRepository)

    @ViewModelScoped
    @Provides
    fun provideSetAuthTypeUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
        settingsRepository: SettingsRepository,
        tinkService: TinkService,
    ) = SetAuthTypeUseCase(
        getAuthUseCase = getAuthUseCase,
        setAuthUseCase = setAuthUseCase,
        settingsRepository = settingsRepository,
        tinkService = tinkService
    )

    @ViewModelScoped
    @Provides
    fun provideVerifyAuthUseCase(
        settingsRepository: SettingsRepository,
        repository: Repository,
    ) = VerifyAuthUseCase(settingsRepository = settingsRepository, repository = repository)


    @ViewModelScoped
    @Provides
    fun provideGetAuthStateFlowUseCase(
        getAuthFlowUseCase: GetAuthFlowUseCase,
        repository: Repository,
    ) = GetAuthStateFlowUseCase(getAuthFlowUseCase = getAuthFlowUseCase, repository = repository)

}
