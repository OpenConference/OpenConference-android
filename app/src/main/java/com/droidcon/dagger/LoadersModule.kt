package com.droidcon.dagger

import com.droidcon.model.LocalStorageSessionsLoader
import com.droidcon.model.ScheduleDataAwareObservableFactory
import com.droidcon.model.SessionsLoader
import com.droidcon.model.backend.schedule.ScheduleDataStateDeterminer
import com.droidcon.model.backend.schedule.ScheduleSync
import com.droidcon.model.database.dao.SessionDao
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