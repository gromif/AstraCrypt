package io.gromif.astracrypt.files.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.domain.repository.item.ItemReader
import io.gromif.astracrypt.files.domain.usecase.GetItemDetailsUseCase
import io.gromif.astracrypt.files.domain.usecase.GetRecentItemsUseCase
import io.gromif.astracrypt.files.domain.usecase.GetValidationRulesUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase

@Module
@InstallIn(SingletonComponent::class)
internal object UseCaseModule {

    @Provides
    fun provideGetValidationRulesUseCase() = GetValidationRulesUseCase()

    @Provides
    fun provideGetRecentItemsUseCase(
        getAeadInfoFlowUseCase: GetAeadInfoFlowUseCase,
        itemReader: ItemReader,
    ) = GetRecentItemsUseCase(
        getAeadInfoFlowUseCase = getAeadInfoFlowUseCase,
        itemReader = itemReader,
    )

    @Provides
    fun provideGetItemDetailsUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        itemReader: ItemReader,
    ) = GetItemDetailsUseCase(getAeadInfoUseCase, itemReader = itemReader)
}
