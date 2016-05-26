package de.droidcon

import com.openconference.OpenConfApp
import com.openconference.dagger.DaggerApplicationComponent
import de.droidcon.dagger.DroidconBerlinBackendModule

/**
 *
 *
 * @author Hannes Dorfmann
 */
class DroidconBerlinApp : OpenConfApp() {

  override fun buildApplicationComponent(): DaggerApplicationComponent.Builder {
    return super.buildApplicationComponent().backendModule(DroidconBerlinBackendModule(this))
  }
}