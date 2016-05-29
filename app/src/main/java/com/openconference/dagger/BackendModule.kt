package com.openconference.dagger

import android.content.Context
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * @author Hannes Dorfmann
 */
@Module
open class BackendModule(c: Context) {

  protected val context: Context
  protected val okHttpClient: OkHttpClient

  init {
    context = c.applicationContext
    okHttpClient = OkHttpClient.Builder()
        .cache(Cache(context.cacheDir, 48 * 1024 * 1024))
        .build()
  }

  @Provides
  @Singleton
  open fun provideOkHttp() = okHttpClient

  @Provides
  @Singleton
  open fun provideBackendAdapter(): BackendScheduleAdapter =
      throw UnsupportedOperationException(
          "Every Build Flavor / conference app has to provide his own BackendAdpater")

}
