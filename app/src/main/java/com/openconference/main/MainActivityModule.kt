package com.openconference.main

import com.openconference.model.screen.*
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
      SpeakersScreen(), TwitterScreen()))

}