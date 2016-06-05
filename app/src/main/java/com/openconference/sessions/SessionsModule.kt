package com.openconference.sessions

import com.openconference.sessions.presentationmodel.PhoneSessionPresentationModelTransformer
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import dagger.Module
import dagger.Provides

/**
 * @author Hannes Dorfmann
 */
@Module
class SessionsModule {

  @Provides
  @SessionsFragmentScope
  fun provideSessionPresentationModelTransformer(): SessionPresentationModelTransformer = PhoneSessionPresentationModelTransformer()
}