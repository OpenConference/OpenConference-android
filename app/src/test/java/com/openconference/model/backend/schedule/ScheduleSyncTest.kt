package com.openconference.model.backend.schedule

import com.hannesdorfmann.sqlbrite.dao.Dao
import com.hannesdorfmann.sqlbrite.dao.DaoManager
import com.openconference.BuildConfig
import com.openconference.model.Location
import com.openconference.model.Session
import com.openconference.model.Speaker
import com.openconference.model.database.LocationAutoValue
import com.openconference.model.database.SessionAutoValue
import com.openconference.model.database.SpeakerAutoValue
import com.openconference.model.database.dao.*
import com.openconference.model.notification.NotificationScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.threeten.bp.Instant
import rx.Observable
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ScheduleSyncTest {

  lateinit var sessionDao: SessionDao
  lateinit var speakerDao: SpeakerDao
  lateinit var locationDao: LocationDao
  lateinit var manager: DaoManager
  var initialized = false;

  @Before
  fun setup() {
    if (!initialized) {
      initialized = true
      sessionDao = SessionDaoSqlite()
      speakerDao = SpeakerDaoSqlite()
      locationDao = LocationDaoSqlite()
      manager = DaoManager.with(RuntimeEnvironment.application)
          .add(sessionDao as Dao)
          .add(locationDao as Dao)
          .add(speakerDao as Dao)
          .databaseName("sync-test")
          .logging(true)
          .version(1)
          .build()
    }

    locationDao.removeAll().toBlocking().first()
    speakerDao.removeAll().toBlocking().first()
    sessionDao.removeAll().toBlocking().first()
  }

  @Test
  fun nothingChanged() {

    val speakerResponse: Observable<BackendScheduleResponse<Speaker>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Speaker>())
    }

    val locationResponse: Observable<BackendScheduleResponse<Location>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Location>())
    }

    val sessionResponse: Observable<BackendScheduleResponse<Session>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Session>())
    }


    val backend = Mockito.mock(BackendScheduleAdapter::class.java)
    Mockito.doReturn(speakerResponse).`when`(backend).getSpeakers()
    Mockito.doReturn(locationResponse).`when`(backend).getLocations()
    Mockito.doReturn(sessionResponse).`when`(backend).getSessions()

    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)

    val spySessionDao = Mockito.spy(sessionDao)
    val spyLocationDao = Mockito.spy(locationDao)
    val spySpeakerDao = Mockito.spy(speakerDao)


    Mockito.verifyNoMoreInteractions(spySessionDao)
    Mockito.verifyNoMoreInteractions(spyLocationDao)
    Mockito.verifyNoMoreInteractions(spySpeakerDao)
    Mockito.verifyNoMoreInteractions(notificationScheduler)


    val sync = ScheduleSync(backend, notificationScheduler, spySessionDao, spySpeakerDao,
        spyLocationDao)


    val result = sync.executeSync().toBlocking().first()
    assertTrue(result)
  }

  @Test
  fun locationChanged() {

    locationDao.insertOrUpdate("1", "notChanged").toBlocking().first()
    locationDao.insertOrUpdate("2", "shouldBeRemoved").toBlocking().first()

    val speakerResponse: Observable<BackendScheduleResponse<Speaker>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Speaker>())
    }

    val locationResponse: Observable<BackendScheduleResponse<Location>> = Observable.defer {
      Observable.just(
          BackendScheduleResponse.dataChanged(
              listOf<Location>(LocationAutoValue.create("1", "notChanged"),
                  LocationAutoValue.create("3", "added"))))
    }

    val sessionResponse: Observable<BackendScheduleResponse<Session>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Session>())
    }


    val backend = Mockito.mock(BackendScheduleAdapter::class.java)
    Mockito.doReturn(speakerResponse).`when`(backend).getSpeakers()
    Mockito.doReturn(locationResponse).`when`(backend).getLocations()
    Mockito.doReturn(sessionResponse).`when`(backend).getSessions()

    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)

    val spySessionDao = Mockito.spy(sessionDao)
    val spySpeakerDao = Mockito.spy(speakerDao)


    Mockito.verifyNoMoreInteractions(spySessionDao)
    Mockito.verifyNoMoreInteractions(notificationScheduler)
    Mockito.verifyNoMoreInteractions(spySpeakerDao)

    val sync = ScheduleSync(backend, notificationScheduler, spySessionDao, spySpeakerDao,
        locationDao)


    val result = sync.executeSync().toBlocking().first()
    assertTrue(result)


    val locations = locationDao.getLocations().toBlocking().first()

    assertEquals(
        listOf(LocationAutoValue.Companion.create("1", "notChanged"),
            LocationAutoValue.Companion.create("3", "added")),
        locations)
  }

  @Test
  fun speakerChanged() {

    speakerDao.insertOrUpdate("1", "notChanged", null, null, null, null, null, null,
        null).toBlocking().first()
    speakerDao.insertOrUpdate("2", "shouldBeRemoved", null, null, null, null, null, null,
        null).toBlocking().first()

    val speakerResponse: Observable<BackendScheduleResponse<Speaker>> = Observable.defer {
      Observable.just(BackendScheduleResponse.dataChanged<Speaker>(listOf(
          SpeakerAutoValue.create("1", "notChanged", null, null, null, null, null, null, null),
          SpeakerAutoValue.create("3", "added", null, null, null, null, null, null, null)
      )))
    }

    val locationResponse: Observable<BackendScheduleResponse<Location>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Location>())
    }

    val sessionResponse: Observable<BackendScheduleResponse<Session>> = Observable.fromCallable {
      BackendScheduleResponse.nothingChanged<Session>()
    }


    val backend = Mockito.mock(BackendScheduleAdapter::class.java)
    Mockito.doReturn(speakerResponse).`when`(backend).getSpeakers()
    Mockito.doReturn(locationResponse).`when`(backend).getLocations()
    Mockito.doReturn(sessionResponse).`when`(backend).getSessions()

    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)

    val spySessionDao = Mockito.spy(sessionDao)
    val spyLocationDao = Mockito.spy(locationDao)


    Mockito.verifyNoMoreInteractions(spySessionDao)
    Mockito.verifyNoMoreInteractions(notificationScheduler)
    Mockito.verifyNoMoreInteractions(spyLocationDao)

    val sync = ScheduleSync(backend, notificationScheduler, spySessionDao, speakerDao,
        spyLocationDao)


    val result = sync.executeSync().toBlocking().first()
    assertTrue(result)


    val speakers = speakerDao.getSpeakers().toBlocking().first()

    assertEquals(
        listOf(
            SpeakerAutoValue.Companion.create("1", "notChanged", null, null, null, null, null, null,
                null),
            SpeakerAutoValue.Companion.create("3", "added", null, null, null, null, null, null,
                null)
        ),
        speakers)
  }

  @Test
  fun sessionChanged() {

    val newTime = Instant.now().plusSeconds(1000)
    val location1 = LocationAutoValue.create("1", "Room1")


    val session1 = SessionAutoValue.create("1", "notChanged", null, null, null, null, null, null,
        false, emptyList())

    val session2 = SessionAutoValue.create("2", "timeChanged", null, null, null, null, null, null,
        true, emptyList())

    val session2Updated = SessionAutoValue.create(session2.id(), session2.title(),
        session2.description(), session2.tags(), newTime, session2.endTime(), session2.locationId(),
        session2.locationName(), session2.favorite(), session2.speakers())

    val session3 = SessionAutoValue.create("3", "toRemove", null, null, null, null, null, null,
        false, emptyList())

    val session4 = SessionAutoValue.create("4", "toAdd", "description", "tags", Instant.now(),
        Instant.now().plusSeconds(3600), location1.id(), location1.name(), false,
        emptyList()) // TODO set speaker

    locationDao.insertOrUpdate(location1.id(), location1.name()).toBlocking().first()
    sessionDao.insertOrUpdate(session1, session1.favorite()).toBlocking().first()
    sessionDao.insertOrUpdate(session2, session2.favorite()).toBlocking().first()
    sessionDao.insertOrUpdate(session3, session3.favorite()).toBlocking().first()

    assertEquals(
        listOf(session1, session2, session3),
        sessionDao.getSessions().toBlocking().first()
    )

    val speakerResponse: Observable<BackendScheduleResponse<Speaker>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Speaker>())
    }

    val locationResponse: Observable<BackendScheduleResponse<Location>> = Observable.defer {
      Observable.just(BackendScheduleResponse.nothingChanged<Location>())
    }

    val sessionResponse: Observable<BackendScheduleResponse<Session>> = Observable.fromCallable {
      BackendScheduleResponse.dataChanged<Session>(listOf(session1, session2Updated, session4))
    }


    val backend = Mockito.mock(BackendScheduleAdapter::class.java)
    Mockito.doReturn(speakerResponse).`when`(backend).getSpeakers()
    Mockito.doReturn(locationResponse).`when`(backend).getLocations()
    Mockito.doReturn(sessionResponse).`when`(backend).getSessions()

    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)

    val spySpeakerDao = Mockito.spy(speakerDao)
    val spyLocationDao = Mockito.spy(locationDao)


    Mockito.verifyNoMoreInteractions(spySpeakerDao)
    Mockito.verifyNoMoreInteractions(spyLocationDao)

    val sync = ScheduleSync(backend, notificationScheduler, sessionDao, spySpeakerDao,
        spyLocationDao)


    val result = sync.executeSync().toBlocking().first()
    assertTrue(result)


    val sessions = sessionDao.getSessions().toBlocking().first()

    assertEquals(
        listOf(session1, session2Updated, session4),
        sessions)

    // TODO verify Alarm manager
  }

}