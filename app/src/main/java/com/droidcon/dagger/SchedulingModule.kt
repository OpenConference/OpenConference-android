package com.droidcon.dagger

import com.droidcon.util.DefaultSchedulerTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger Module providing Threading and Scheduling related stuff
 * @author Hannes Dorfmann
 */
@Module
@Singleton
class SchedulingModule internal constructor() {

  @Provides
  @Singleton
  fun provideSchedulerTransformer() = DefaultSchedulerTransformer()

}
