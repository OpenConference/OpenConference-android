package de.droidcon.dagger

import android.content.Context
import com.github.aurae.retrofit2.LoganSquareConverterFactory
import com.openconference.dagger.NetworkModule
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import de.droidcon.model.backend.DroidconBerlinBackend
import de.droidcon.model.backend.DroidconBerlinBackendScheduleAdapter
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

class DroidconBerlinNetworkModule(context: Context) : NetworkModule(context) {

  private val retrofit: Retrofit
  private val okHttp: OkHttpClient
  private val backendAdapter: BackendScheduleAdapter
  private val backend: DroidconBerlinBackend

  init {
    okHttp = OkHttpClient.Builder().cache(Cache(context.cacheDir, 48 * 1024 * 1024))
        .build()

    retrofit = Retrofit.Builder()
        .client(okHttp)
        .baseUrl("http://droidcon.de/rest/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(LoganSquareConverterFactory.create())
        .build()


    backend = retrofit.create(DroidconBerlinBackend::class.java)

    backendAdapter = DroidconBerlinBackendScheduleAdapter(backend)
  }

  override fun provideOkHttp(): OkHttpClient = okHttp

  override fun provideBackendAdapter(): BackendScheduleAdapter = backendAdapter
}