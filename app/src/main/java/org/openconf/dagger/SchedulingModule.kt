package org.openconf.dagger

import com.droidcon.util.DefaultSchedulerTransformer
import com.droidcon.util.SchedulerTransformer
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
