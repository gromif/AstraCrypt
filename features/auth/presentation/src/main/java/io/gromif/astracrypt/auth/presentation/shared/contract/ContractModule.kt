package io.gromif.astracrypt.auth.presentation.shared.contract

import contract.auth.AuthContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.gromif.astracrypt.auth.domain.usecase.SetLastActiveTimeUseCase
import io.gromif.astracrypt.auth.domain.usecase.auth.state.GetAuthStateFlowUseCase
import io.gromif.astracrypt.auth.domain.usecase.timeout.CheckAuthTimeoutUseCase

@Module
@InstallIn(ViewModelComponent::class)
internal object ContractModule {

    @Provides
    fun provideAuthContract(
        setLastActiveTimeUseCase: SetLastActiveTimeUseCase,
        checkAuthTimeoutUseCase: CheckAuthTimeoutUseCase,
        getAuthStateFlowUseCase: GetAuthStateFlowUseCase
    ): AuthContract = AuthContractImpl(
        setLastActiveTimeUseCase = setLastActiveTimeUseCase,
        checkAuthTimeoutUseCase = checkAuthTimeoutUseCase,
        getAuthStateFlowUseCase = getAuthStateFlowUseCase
    )

}