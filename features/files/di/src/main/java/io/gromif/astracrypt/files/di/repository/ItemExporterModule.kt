package io.gromif.astracrypt.files.di.repository

import android.content.Context
import android.net.Uri
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.db.FilesDao
import io.gromif.astracrypt.files.data.repository.DefaultItemExporter
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.domain.repository.ItemExporter
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.io.FilesUtil

@Module
@InstallIn(SingletonComponent::class)
internal object ItemExporterModule {

    @Provides
    fun provideItemExporter(
        @ApplicationContext context: Context,
        filesDao: FilesDao,
        filesUtil: FilesUtil,
        fileHandler: FileHandler,
        uriToString: Mapper<Uri, String>,
        stringToUri: Mapper<String, Uri>,
    ): ItemExporter = DefaultItemExporter(
        context = context,
        filesDao = filesDao,
        fileHandler = fileHandler,
        filesUtil = filesUtil,
        uriToString = uriToString,
        stringToUri = stringToUri
    )

}