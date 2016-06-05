package com.openconference.dagger

import com.openconference.model.backend.schedule.BackendScheduleAdapter
import com.openconference.model.backend.schedule.ScheduleSync
import com.openconference.model.database.dao.LocationDao
import com.openconference.model.database.dao.SessionDao
import com.openconference.model.database.dao.SpeakerDao
import com.openconference.model.notification.NotificationScheduler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module(includes = arrayOf(
    DaoModule::class,
    LoadersModule::class,
    SchedulingModule::class,
    BackendModule::class,
    ScheduleModule::class,
    ErrorMessageModule::class)
)
class ApplicationModule {

  @Provides
  @Singleton
  fun provideScheduleSync(backend: BackendScheduleAdapter,
      notificationScheduler: NotificationScheduler,
      sessionDao: SessionDao,
      speakerDao: SpeakerDao,
      locationDao: LocationDao) = ScheduleSync(backend, notificationScheduler, sessionDao,
      speakerDao, locationDao)

}
