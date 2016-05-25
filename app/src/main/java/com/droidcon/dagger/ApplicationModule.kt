package com.droidcon.dagger

import com.droidcon.model.backend.schedule.BackendScheduleAdapter
import com.droidcon.model.backend.schedule.ScheduleSync
import com.droidcon.model.database.dao.LocationDao
import com.droidcon.model.database.dao.SessionDao
import com.droidcon.model.database.dao.SpeakerDao
import com.droidcon.model.notification.NotificationScheduler
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
    ScheduleModule::class)
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
