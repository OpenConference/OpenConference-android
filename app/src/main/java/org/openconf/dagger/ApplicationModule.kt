package org.openconf.dagger

import org.openconf.model.backend.schedule.BackendScheduleAdapter
import org.openconf.model.backend.schedule.ScheduleSync
import org.openconf.model.database.dao.LocationDao
import org.openconf.model.database.dao.SessionDao
import org.openconf.model.database.dao.SpeakerDao
import org.openconf.model.notification.NotificationScheduler
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
