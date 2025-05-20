package io.gromif.astracrypt.files.files.util

import io.gromif.astracrypt.files.di.DataSources
import io.gromif.astracrypt.files.domain.usecase.data.GetFilesDataFlow
import io.gromif.astracrypt.files.domain.usecase.data.InvalidateDataSourceUseCase
import io.gromif.astracrypt.files.domain.usecase.search.RequestSearchUseCase
import javax.inject.Inject

internal data class DataUseCases<T> @Inject constructor(
    @DataSources.Default
    val getFilesDataFlow: GetFilesDataFlow<T>,
    @DataSources.Starred
    val getStarredDataFlow: GetFilesDataFlow<T>,

    val invalidateDataSourceUseCase: InvalidateDataSourceUseCase<T>,
    val requestSearchUseCase: RequestSearchUseCase
)
