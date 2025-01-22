package io.gromif.astracrypt.files.di.import_service

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import coil.ImageLoader
import coil.request.ImageRequest
import com.nevidimka655.astracrypt.utils.Api
import com.nevidimka655.astracrypt.utils.Mapper
import com.nevidimka655.astracrypt.utils.io.MediaMetadataRetrieverCompat
import com.nevidimka655.astracrypt.utils.io.Randomizer
import com.nevidimka655.crypto.tink.data.AssociatedDataManager
import com.nevidimka655.crypto.tink.data.KeysetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.gromif.astracrypt.files.data.factory.flags.AudioFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.ImageFlagsFactory
import io.gromif.astracrypt.files.data.factory.flags.VideoFlagsFactory
import io.gromif.astracrypt.files.data.factory.preview.AudioPreviewFactory
import io.gromif.astracrypt.files.data.factory.preview.DefaultPreviewFactory
import io.gromif.astracrypt.files.data.util.BitmapCompressor
import io.gromif.astracrypt.files.data.util.FileHandler
import io.gromif.astracrypt.files.data.util.FileUtilImpl
import io.gromif.astracrypt.files.data.util.FlagsUtilImpl
import io.gromif.astracrypt.files.data.util.PreviewUtilImpl
import io.gromif.astracrypt.files.di.FilesImageLoader
import io.gromif.astracrypt.files.domain.repository.SettingsRepository
import io.gromif.astracrypt.files.domain.util.FileUtil
import io.gromif.astracrypt.files.domain.util.FlagsUtil
import io.gromif.astracrypt.files.domain.util.PreviewUtil

@Module
@InstallIn(SingletonComponent::class)
internal object UtilModule {

    @Provides
    fun provideFileHandler(
        @ApplicationContext context: Context,
        keysetManager: KeysetManager,
        associatedDataManager: AssociatedDataManager,
        settingsRepository: SettingsRepository,
        randomizer: Randomizer
    ): FileHandler = FileHandler(
        keysetManager = keysetManager,
        associatedDataManager = associatedDataManager,
        settingsRepository = settingsRepository,
        randomizer = randomizer,
        filesDir = context.filesDir
    )

    @Provides
    fun provideFileUtil(
        @ApplicationContext context: Context,
        uriMapper: Mapper<String, Uri>,
        fileHandler: FileHandler,
    ): FileUtil = FileUtilImpl(
        context = context,
        fileHandler = fileHandler,
        uriMapper = uriMapper
    )

    @Provides
    fun provideFlagsUtil(
        @ApplicationContext context: Context,
        uriMapper: Mapper<String, Uri>,
        mediaMetadataRetrieverCompat: MediaMetadataRetrieverCompat,
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
            mediaMetadataRetrieverCompat = mediaMetadataRetrieverCompat,
            uriMapper = uriMapper
        )
    )

    @Provides
    fun providePreviewUtil(
        @ApplicationContext context: Context,
        @FilesImageLoader imageLoader: ImageLoader,
        @ImportImageRequestBuilder imageRequestBuilder: ImageRequest.Builder,

        fileHandler: FileHandler,
        mediaMetadataRetrieverCompat: MediaMetadataRetrieverCompat,
        bitmapCompressor: BitmapCompressor,
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
            mediaMetadataRetrieverCompat = mediaMetadataRetrieverCompat,
            imageRequestBuilder = imageRequestBuilder,
            imageLoader = imageLoader,
            bitmapCompressor = bitmapCompressor
        )
    )

    @Provides
    fun provideBitmapCompressor(): BitmapCompressor = BitmapCompressor(
        compressFormat = if (Api.atLeast11()) Bitmap.CompressFormat.WEBP_LOSSY else {
            Bitmap.CompressFormat.WEBP
        }
    )

}