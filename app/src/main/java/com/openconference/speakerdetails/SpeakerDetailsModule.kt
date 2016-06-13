package com.openconference.sessiondetails

import android.app.Activity
import com.openconference.Navigator
import com.openconference.PhoneNavigator
import com.openconference.sessiondetails.presentationmodel.PhoneSpeakerDetailsPresentationModelTransformer
import com.openconference.sessiondetails.presentationmodel.SpeakerDetailsPresentationModelTransformer
import dagger.Module
import dagger.Provides

/**
 * Dagger Module for SessionDetails
 * @author Hannes Dorfmann
 */
@Module
class SpeakerDetailsModule(private val activity: Activity) {

  @Provides
  @SpeakerDetailsScope
  fun provideNavigator(): Navigator = PhoneNavigator(activity)

  @Provides
  @SpeakerDetailsScope
  fun providePresentationModelTransformer(): SpeakerDetailsPresentationModelTransformer =
      PhoneSpeakerDetailsPresentationModelTransformer()
}