package com.openconference.sessions

import android.app.Activity
import com.openconference.Navigator
import com.openconference.PhoneNavigator
import com.openconference.speakers.SpeakersScope
import dagger.Module
import dagger.Provides

/**
 * @author Hannes Dorfmann
 */
@Module
class SpeakersModule(private val activity: Activity) {

  @Provides
  @SpeakersScope
  fun provideNavigator(): Navigator = PhoneNavigator(activity)

}