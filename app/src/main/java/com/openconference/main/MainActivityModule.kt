package com.openconference.main

import android.app.Activity
import com.openconference.Navigator
import com.openconference.PhoneNavigator
import com.openconference.model.screen.*
import dagger.Module
import dagger.Provides

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class MainActivityModule(private val activity: Activity) {

  @Provides
  @MainActivityScope
  fun provideScreens(): Screens = Screens(listOf(SessionsScreen(), MyScheduleScreen(),
      SpeakersScreen(), TwitterScreen()))

  @Provides
  @MainActivityScope
  fun provideNavigator(): Navigator = PhoneNavigator(activity)
}