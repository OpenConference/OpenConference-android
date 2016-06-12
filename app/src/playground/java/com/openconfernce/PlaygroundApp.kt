package com.openconfernce

import com.openconference.OpenConfApp

/**
 * Application for demo purpose displaying som mocked data
 *
 * @author Hannes Dorfmann
 */
class PlaygroundApp : OpenConfApp() {

  override fun buildApplicationComponent() =
      super.buildApplicationComponent().networkModule(MockNetworkModule(this))

}