package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.Repository
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetDatabaseAeadUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object AeadUseCaseModule {

    @Provides
    fun provideGetAeadInfoFlowUseCase(settingsRepository: SettingsRepository) =
        GetAeadInfoFlowUseCase(settingsRepository)

    @Provides
    fun provideGetAeadInfoUseCase(settingsRepository: SettingsRepository) =
        GetAeadInfoUseCase(settingsRepository)

    @Provides
    fun provideSetAeadInfoUseCase(settingsRepository: SettingsRepository) =
        SetAeadInfoUseCase(settingsRepository)

    @Provides
    fun provideSetDatabaseAeadUseCase(
        setAeadInfoUseCase: SetAeadInfoUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        repository: Repository,
    ) = SetDatabaseAeadUseCase(
        setAeadInfoUseCase = setAeadInfoUseCase,
        getAeadInfoUseCase = getAeadInfoUseCase,
        repository = repository
    )

}