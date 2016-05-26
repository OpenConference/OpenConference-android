package com.openconference.dagger

import android.content.Context
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author Hannes Dorfmann
 */
@Module
open class BackendModule(c: Context) {

  protected val context: Context

  init {
    context = c.applicationContext
  }

  @Provides
  @Singleton
  open fun provideOkHttp() =
      OkHttpClient.Builder().cache(Cache(context.cacheDir, 48 * 1024 * 1024))
          .build()

  @Provides
  @Singleton
  open fun provideRetrofit(client: OkHttpClient): Retrofit =
      throw UnsupportedOperationException(
          "Every Build Flavor / Conference app has to provide his own Retrofit instance")

  @Provides
  @Singleton
  open fun provideBackendAdapter(): BackendScheduleAdapter =
      throw UnsupportedOperationException(
          "Every Build Flavor / conference app has to provide his own BackendAdpater")

}
