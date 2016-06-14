package de.droidcon

import com.crashlytics.android.Crashlytics
import com.openconference.OpenConfApp
import com.openconference.dagger.DaggerApplicationComponent
import com.twitter.sdk.android.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import de.droidcon.dagger.DroidconBerlinNetworkModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 *
 *
 * @author Hannes Dorfmann
 */
class DroidconBerlinApp : OpenConfApp() {


  override fun onCreate() {

    val config = TwitterAuthConfig("API-KEY",
        "API-SECRET")
    Fabric.with(this, Crashlytics(), Twitter(config));


    super.onCreate()
  }

  override fun buildApplicationComponent(): DaggerApplicationComponent.Builder {
    return super.buildApplicationComponent().networkModule(DroidconBerlinNetworkModule(this))
  }

  override fun plantDebugTimberTree() {
    plantProductionTimberTree()
  }

  override fun plantProductionTimberTree() {
    super.plantProductionTimberTree()

    Timber.plant(CrashlyticsTimberTree())

  }
}