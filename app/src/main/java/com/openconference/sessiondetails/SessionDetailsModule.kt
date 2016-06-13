package com.openconference.sessiondetails

import android.app.Activity
import com.openconference.Navigator
import com.openconference.PhoneNavigator
import com.openconference.sessiondetails.presentationmodel.PhoneSessionDetailsPresentationModelTransformer
import com.openconference.sessiondetails.presentationmodel.SessionDetailsPresentationModelTransformer
import dagger.Module
import dagger.Provides

/**
 * Dagger Module for SessionDetails
 * @author Hannes Dorfmann
 */
@Module
class SessionDetailsModule(private val activity: Activity) {

  @Provides
  @SessionDetailsScope
  fun provideNavigator(): Navigator = PhoneNavigator(activity)

  @Provides
  @SessionDetailsScope
  fun providePresentationModelTransformer(): SessionDetailsPresentationModelTransformer =
      PhoneSessionDetailsPresentationModelTransformer()
}