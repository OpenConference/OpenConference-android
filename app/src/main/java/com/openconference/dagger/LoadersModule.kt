package com.openconference.dagger

import com.openconference.model.*
import com.openconference.model.backend.schedule.ScheduleDataStateDeterminer
import com.openconference.model.backend.schedule.ScheduleSync
import com.openconference.model.database.dao.SessionDao
import com.openconference.model.database.dao.SpeakerDao
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
      sessionDao: SessionDao): SessionsLoader = LocalStorageSessionsLoader(factory, sessionDao)

  @Provides
  @Singleton
  fun provideSpeakersLoader(factory: ScheduleDataAwareObservableFactory,
      speakerDao: SpeakerDao): SpeakersLoader = LocalStorageSpeakersLoader(factory, speakerDao)

}