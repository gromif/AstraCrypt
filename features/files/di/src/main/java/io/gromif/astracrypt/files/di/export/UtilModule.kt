package io.gromif.astracrypt.files.di.export

import android.content.Context
import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.util.ExportUtil
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.FilesUtil

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Provides
    fun provideExportUtil(
        @ApplicationContext context: Context,
        filesDao: FilesDao,
        fileHandler: FileHandler,
        filesUtil: FilesUtil,
        stringToUriMapper: Mapper<Uri, String>,
    ): ExportUtil = ExportUtil(
        context = context,
        filesDao = filesDao,
        fileHandler = fileHandler,
        filesUtil = filesUtil,
        stringToUriMapper = stringToUriMapper,
    )

}