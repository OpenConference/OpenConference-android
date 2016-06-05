package com.openconference.dagger

import com.openconference.model.errormessage.DefaultErrorMessageDeterminer
import com.openconference.model.errormessage.ErrorMessageDeterminer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for Error Message
 *
 * @author Hannes Dorfmann
 */
@Module
class ErrorMessageModule {

  @Provides
  @Singleton
  fun provideErrorMessageDeterminer() : ErrorMessageDeterminer = DefaultErrorMessageDeterminer()
}