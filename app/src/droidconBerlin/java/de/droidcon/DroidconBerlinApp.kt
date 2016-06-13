package de.droidcon

import com.openconference.OpenConfApp
import com.openconference.dagger.DaggerApplicationComponent
import de.droidcon.dagger.DroidconBerlinNetworkModule

/**
 *
 *
 * @author Hannes Dorfmann
 */
class DroidconBerlinApp : OpenConfApp() {

  override fun buildApplicationComponent(): DaggerApplicationComponent.Builder {
    return super.buildApplicationComponent().networkModule(DroidconBerlinNetworkModule(this))
  }
}