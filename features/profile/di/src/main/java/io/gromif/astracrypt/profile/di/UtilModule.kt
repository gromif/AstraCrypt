package io.gromif.astracrypt.profile.di

import android.content.Context
import android.graphics.Bitmap
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import io.gromif.astracrypt.profile.data.util.ExternalIconUtil
import io.gromif.astracrypt.profile.data.util.FileUtil
import io.gromif.astracrypt.profile.data.util.PreviewUtil
import io.gromif.astracrypt.profile.data.util.preview.CoilPreviewUtil
import io.gromif.astracrypt.profile.data.util.preview.NativePreviewUtil
import io.gromif.astracrypt.utils.io.BitmapCompressor
import io.gromif.crypto.tink.data.AssociatedDataManager
import io.gromif.crypto.tink.data.KeysetManager
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
internal object UtilModule {

    @ViewModelScoped
    @Provides
    fun provideExternalIconUtil(
        previewUtil: PreviewUtil,
        @AvatarBitmapCompressor
        bitmapCompressor: BitmapCompressor,
        fileUtil: FileUtil,
    ) = ExternalIconUtil(
        previewUtil = previewUtil,
        bitmapCompressor = bitmapCompressor,
        fileUtil = fileUtil,
    )

    @ViewModelScoped
    @Provides
    fun provideFileUtil(
        @ApplicationContext context: Context,
        keysetManager: KeysetManager,
        associatedDataManager: AssociatedDataManager
    ) = FileUtil(
        keysetManager = keysetManager,
        associatedDataManager = associatedDataManager,
        filesDir = context.filesDir
    )

    @ViewModelScoped
    @Provides
    fun providePreviewUtil(
        nativePreviewUtil: NativePreviewUtil,
        coilPreviewUtil: CoilPreviewUtil,
    ) = PreviewUtil(nativePreviewUtil, coilPreviewUtil)

    @ViewModelScoped
    @Provides
    fun provideNativePreviewUtil(@ApplicationContext context: Context) = NativePreviewUtil(context)

    @ViewModelScoped
    @Provides
    fun provideCoilPreviewUtil(
        @ApplicationContext context: Context,
        @AvatarImageLoader
        imageLoader: ImageLoader,
    ) = CoilPreviewUtil(context, imageLoader)

    @AvatarBitmapCompressor
    @ViewModelScoped
    @Provides
    fun provideBitmapCompressor() = BitmapCompressor(compressFormat = Bitmap.CompressFormat.PNG)

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AvatarBitmapCompressor