package io.gromif.astracrypt.auth.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.auth.domain.repository.Repository
import io.gromif.astracrypt.auth.domain.service.ClockService
import io.gromif.astracrypt.auth.domain.usecase.SetLastActiveTimeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.GetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.SetAuthUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.CheckAuthTimeoutUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.SetTimeoutUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object TimeoutUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideSetTimeoutUseCase(
        getAuthUseCase: GetAuthUseCase,
        setAuthUseCase: SetAuthUseCase,
    ) = SetTimeoutUseCase(getAuthUseCase = getAuthUseCase, setAuthUseCase = setAuthUseCase)

    @ViewModelScoped
    @Provides
    fun provideSetLastActiveTimeUseCase(
        getAuthStateFlowUseCase: GetAuthStateFlowUseCase,
        clockService: ClockService
    ) = SetLastActiveTimeUseCase(
        getAuthStateFlowUseCase = getAuthStateFlowUseCase,
        clockService = clockService
    )

    @ViewModelScoped
    @Provides
    fun provideCheckAuthTimeoutUseCase(
        getAuthUseCase: GetAuthUseCase,
        clockService: ClockService,
        repository: Repository
    ) = CheckAuthTimeoutUseCase(
        getAuthUseCase = getAuthUseCase,
        clockService = clockService,
        repository = repository
    )

}
