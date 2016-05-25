package com.openconference.dagger

import com.openconference.util.DefaultSchedulerTransformer
import com.openconference.util.SchedulerTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module providing Threading and Scheduling related stuff
 * @author Hannes Dorfmann
 */
@Module
class SchedulingModule() {

  @Provides
  @Singleton
  fun provideSchedulerTransformer() :SchedulerTransformer = DefaultSchedulerTransformer()

}
