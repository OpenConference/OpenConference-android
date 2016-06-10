package com.openconference.main

import com.openconference.model.screen.MyScheduleScreen
import com.openconference.model.screen.Screens
import com.openconference.model.screen.SessionsScreen
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
  fun provideScreens(): Screens = Screens(listOf(SessionsScreen(), MyScheduleScreen(),
      SessionsScreen()))

}