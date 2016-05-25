package org.openconf.dagger

import org.openconf.model.LocalStorageSessionsLoader
import org.openconf.model.ScheduleDataAwareObservableFactory
import org.openconf.model.SessionsLoader
import org.openconf.model.backend.schedule.ScheduleDataStateDeterminer
import org.openconf.model.backend.schedule.ScheduleSync
import org.openconf.model.database.dao.SessionDao
import dagger.Module
import dagger.Provides
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class LoadersModule {

  @Provides
  @Singleton
  fun providesScheduleDataAwareObservableFactory(scheduleSync: ScheduleSync,
      scheduleDataStateDeterminer: ScheduleDataStateDeterminer) =
      ScheduleDataAwareObservableFactory(scheduleSync, scheduleDataStateDeterminer, Schedulers.io())

  @Provides
  @Singleton
  fun providesSessionLoader(factory: ScheduleDataAwareObservableFactory,
      sessionDao: SessionDao) : SessionsLoader = LocalStorageSessionsLoader(factory, sessionDao)

}