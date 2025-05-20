package io.gromif.astracrypt.files.files.util

import io.gromif.astracrypt.files.di.DataSources
import io.gromif.astracrypt.files.domain.usecase.GetDataFlowUseCase
import io.gromif.astracrypt.files.domain.usecase.search.RequestSearchUseCase
import javax.inject.Inject

internal data class DataUseCases<T> @Inject constructor(
    @DataSources.Default
    val getDataFlowUseCase: GetDataFlowUseCase<T>,
    @DataSources.Starred
    val getStarredDataFlow: GetDataFlowUseCase<T>,

    val requestSearchUseCase: RequestSearchUseCase
)
