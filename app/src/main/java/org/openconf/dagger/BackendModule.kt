package org.openconf.dagger

import org.openconf.model.backend.schedule.BackendScheduleAdapter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Hannes Dorfmann
 */
@Module
class BackendModule {

  @Provides
  @Singleton
  fun provideBackendAdapter(): BackendScheduleAdapter =
      throw UnsupportedOperationException(
          "Every Build Flavor / conference app has to provide his own BackendAdpater")

}
