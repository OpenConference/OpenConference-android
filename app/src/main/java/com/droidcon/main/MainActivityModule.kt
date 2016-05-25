package com.droidcon.main

import com.droidcon.model.screen.Screens
import com.droidcon.model.screen.SessionsScreen
import dagger.Module
import dagger.Provides

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