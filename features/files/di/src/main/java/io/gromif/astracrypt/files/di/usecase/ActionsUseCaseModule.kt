package io.gromif.astracrypt.files.di.usecase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.files.domain.repository.item.ItemDeleter
import io.gromif.astracrypt.files.domain.repository.item.ItemWriter
import io.gromif.astracrypt.files.domain.usecase.actions.CreateFolderUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.DeleteUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.MoveUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.RenameUseCase
import io.gromif.astracrypt.files.domain.usecase.actions.SetStateUseCase
import io.gromif.astracrypt.files.domain.usecase.aead.GetAeadInfoUseCase
import io.gromif.astracrypt.files.domain.usecase.navigator.GetCurrentNavFolderFlowUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object ActionsUseCaseModule {

    @Provides
    fun provideCreateFolderUseCase(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        getAeadInfoUseCase: GetAeadInfoUseCase,
        itemWriter: ItemWriter,
    ) = CreateFolderUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        getAeadInfoUseCase = getAeadInfoUseCase,
        itemWriter = itemWriter
    )

    @Provides
    fun provideDeleteUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        itemDeleter: ItemDeleter,
    ) = DeleteUseCase(getAeadInfoUseCase, itemDeleter = itemDeleter)

    @Provides
    fun provideMoveUseCase(
        getCurrentNavFolderFlowUseCase: GetCurrentNavFolderFlowUseCase,
        itemWriter: ItemWriter
    ) = MoveUseCase(
        getCurrentNavFolderFlowUseCase = getCurrentNavFolderFlowUseCase,
        itemWriter = itemWriter
    )

    @Provides
    fun provideRenameUseCase(
        getAeadInfoUseCase: GetAeadInfoUseCase,
        itemWriter: ItemWriter,
    ) = RenameUseCase(getAeadInfoUseCase, itemWriter = itemWriter)

    @Provides
    fun provideSetStarredUseCase(itemWriter: ItemWriter) = SetStateUseCase(itemWriter = itemWriter)

}