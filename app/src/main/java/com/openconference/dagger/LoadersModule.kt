package com.openconference.dagger

import com.openconference.model.LocalStorageSessionsLoader
import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.SessionsLoader
import com.openconference.model.backend.schedule.ScheduleDataStateDeterminer
import com.openconference.model.backend.schedule.ScheduleSync
import com.openconference.model.database.dao.SessionDao
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