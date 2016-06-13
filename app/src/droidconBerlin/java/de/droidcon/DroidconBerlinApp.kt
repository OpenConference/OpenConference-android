package de.droidcon

import com.crashlytics.android.Crashlytics
import com.openconference.OpenConfApp
import com.openconference.dagger.DaggerApplicationComponent
import de.droidcon.dagger.DroidconBerlinNetworkModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber

/**
 *
 *
 * @author Hannes Dorfmann
 */
class DroidconBerlinApp : OpenConfApp() {

  override fun buildApplicationComponent(): DaggerApplicationComponent.Builder {
    return super.buildApplicationComponent().networkModule(DroidconBerlinNetworkModule(this))
  }

  override fun plantDebugTimberTree() {
    plantProductionTimberTree()
  }

  override fun plantProductionTimberTree() {
    super.plantProductionTimberTree()

    Fabric.with(this, Crashlytics());
    Timber.plant(CrashlyticsTimberTree())

  }
}