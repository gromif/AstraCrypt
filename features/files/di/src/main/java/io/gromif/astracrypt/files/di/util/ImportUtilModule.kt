package io.gromif.astracrypt.files.di.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import coil3.ImageLoader
import coil3.request.ImageRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.dto.FileFlagsDto
import io.gromif.astracrypt.files.data.factory.flags.AudioFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.ImageFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.VideoFlagsFactory
import io.gromif.astracrypt.files.data.factory.preview.AudioPreviewFactory
import io.gromif.astracrypt.files.data.factory.preview.DefaultPreviewFactory
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.data.util.FileUtilFactoryImpl
import io.gromif.astracrypt.files.data.util.FlagsUtilImpl
import io.gromif.astracrypt.files.data.util.PreviewUtilImpl
import io.gromif.astracrypt.files.di.coil.FilesImageLoader
import io.gromif.astracrypt.files.di.coil.ImportImageRequestBuilder
import io.gromif.astracrypt.files.domain.repository.AeadSettingsRepository
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil
import io.gromif.astracrypt.utils.Api
import io.gromif.astracrypt.utils.Mapper
import io.gromif.astracrypt.utils.Serializer
import io.gromif.astracrypt.utils.io.BitmapCompressor
import io.gromif.astracrypt.utils.io.Randomizer
import io.gromif.crypto.tink.aead.AeadManager
import io.gromif.crypto.tink.keyset.associated_data.AssociatedDataManager
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
internal object ImportUtilModule {

    @Provides
    fun provideFileHandler(
        @ApplicationContext context: Context,
        aeadManager: AeadManager,
        associatedDataManager: AssociatedDataManager,
        aeadSettingsRepository: AeadSettingsRepository,
        randomizer: Randomizer
    ): FileHandler = FileHandler(
        aeadManager = aeadManager,
        associatedDataManager = associatedDataManager,
        aeadSettingsRepository = aeadSettingsRepository,
        randomizer = randomizer,
        filesDir = context.filesDir
    )

    @Provides
    fun provideFileUtilFactory(
        @ApplicationContext context: Context,
        uriMapper: Mapper<String, Uri>,
        fileHandler: FileHandler,
    ): FileUtil.Factory = FileUtilFactoryImpl(
        context = context,
        fileHandler = fileHandler,
        uriMapper = uriMapper
    )

    @Provides
    fun provideFlagsUtil(
        @ApplicationContext context: Context,
        uriMapper: Mapper<String, Uri>,
        serializer: Serializer<FileFlagsDto, String>,
    ): FlagsUtil = FlagsUtilImpl(
        audioFlagsFactory = AudioFlagsFactory(
            contentResolver = context.contentResolver,
            uriMapper = uriMapper
        ),
        imageFlagsFactory = ImageFlagsFactory(
            contentResolver = context.contentResolver,
            uriMapper = uriMapper
        ),
        videoFlagsFactory = VideoFlagsFactory(
            context = context,
            uriMapper = uriMapper
        ),
        serializer = serializer,
    )

    @Provides
    fun providePreviewUtil(
        @ApplicationContext context: Context,
        @FilesImageLoader imageLoader: ImageLoader,
        @ImportImageRequestBuilder imageRequestBuilder: ImageRequest.Builder,
        @FilesBitmapCompressor bitmapCompressor: BitmapCompressor,

        fileHandler: FileHandler,
        uriMapper: Mapper<String, Uri>,
    ): PreviewUtil = PreviewUtilImpl(
        fileHandler = fileHandler,
        defaultPreviewFactory = DefaultPreviewFactory(
            uriMapper = uriMapper,
            imageRequestBuilder = imageRequestBuilder,
            imageLoader = imageLoader,
            bitmapCompressor = bitmapCompressor
        ),
        audioPreviewFactory = AudioPreviewFactory(
            uriMapper = uriMapper,
            contentResolver = context.contentResolver,
            imageRequestBuilder = imageRequestBuilder,
            imageLoader = imageLoader,
            bitmapCompressor = bitmapCompressor
        )
    )

    @FilesBitmapCompressor
    @Provides
    fun provideBitmapCompressor(): BitmapCompressor = BitmapCompressor(
        compressFormat = if (Api.atLeast11()) {
            Bitmap.CompressFormat.WEBP_LOSSY
        } else {
            Bitmap.CompressFormat.WEBP
        }
    )

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class FilesBitmapCompressor
}
