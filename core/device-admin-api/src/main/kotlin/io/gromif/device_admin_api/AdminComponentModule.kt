package io.gromif.device_admin_api

import android.content.ComponentName
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object AdminComponentModule {

    @Provides
    fun provideAdminComponentImpl(@ApplicationContext context: Context) =
        ComponentName(context, AdminReceiver::class.java)

}