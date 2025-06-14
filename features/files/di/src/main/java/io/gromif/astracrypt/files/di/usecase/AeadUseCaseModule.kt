package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.AeadManager
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.SetDatabaseAeadUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object AeadUseCaseModule {

    @Provides
    fun provideGetAeadInfoFlowUseCase(aeadSettingsRepository: AeadSettingsRepository) =
        GetAeadInfoFlowUseCase(aeadSettingsRepository)

    @Provides
    fun provideGetAeadInfoUseCase(aeadSettingsRepository: AeadSettingsRepository) =
        GetAeadInfoUseCase(aeadSettingsRepository)

    @Provides
    fun provideSetAeadInfoUseCase(aeadSettingsRepository: AeadSettingsRepository) =
        SetAeadInfoUseCase(aeadSettingsRepository)

    @Provides
    fun provideSetDatabaseAeadUseCase(
        setAeadInfoUseCase: SetAeadInfoUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        aeadManager: AeadManager,
    ) = SetDatabaseAeadUseCase(
        setAeadInfoUseCase = setAeadInfoUseCase,
        getAeadInfoUseCase = getAeadInfoUseCase,
        aeadManager = aeadManager
    )
}
