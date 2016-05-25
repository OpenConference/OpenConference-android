package org.openconf.main

import dagger.Module
import dagger.Provides
import org.openconf.model.screen.Screens
import org.openconf.model.screen.SessionsScreen

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class MainActivityModule {

  @Provides
  @MainActivityScope
  fun provideScreens(): Screens = Screens(listOf(SessionsScreen()))

}