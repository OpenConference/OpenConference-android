package com.openconference.dagger

import android.content.Context
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class PicassoModule(c: Context) {

  private val context = c.applicationContext

  @Singleton
  @Provides
  fun providePicasso(okhttp: OkHttpClient) =
      Picasso.Builder(context).downloader(OkHttp3Downloader(okhttp)).build()

}