package com.openconference.model.database.dao

import com.openconference.BuildConfig
import com.openconference.model.database.SessionAutoValue
import com.openconference.model.database.SpeakerAutoValue
import com.hannesdorfmann.sqlbrite.dao.Dao
import com.hannesdorfmann.sqlbrite.dao.DaoManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.threeten.bp.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 *
 *
 * @author Hannes Dorfmann
 */
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class SessionDaoSqliteTest {

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
          .databaseName("session-test")
          .logging(true)
          .version(1)
          .build()
    }

    sessionDao.removeAll()
    speakerDao.removeAll()
    locationDao.removeAll()
  }

  @Test
  fun sessionWithSpeakersAndLocation() {

    val locationId1 = "loc1"
    val locationName1 = "location1"

    val speaker1 = SpeakerAutoValue.create("sp1", "spname1", null, "pic1", null, null, null, null,
        null)
    val speaker2 = SpeakerAutoValue.create("sp2", "spname2", null, null, null, null, null, null,
        null)

    val session1 = SessionAutoValue.create("1", "Title1", "Description1", "tags1", Instant.now(),
        Instant.now(), locationId1, locationName1, true, listOf(speaker1, speaker2))

    val session2 = SessionAutoValue.create("2", "Title2", "Description2", "tags2", Instant.now(),
        Instant.now(), locationId1, locationName1, true, listOf(speaker2))


    locationDao.insertOrUpdate(locationId1, locationName1).toBlocking().first()
    speakerDao.insertOrUpdate(speaker1.id(), speaker1.name(), speaker1.info(),
        speaker1.profilePic(), speaker1.company(), speaker1.jobTitle(), speaker1.link1(),
        speaker1.link2(), speaker1.link3()).toBlocking().first()

    speakerDao.insertOrUpdate(speaker2.id(), speaker2.name(), speaker2.info(),
        speaker2.profilePic(), speaker2.company(), speaker2.jobTitle(), speaker2.link1(),
        speaker2.link2(), speaker2.link3()).toBlocking().first()

    sessionDao.addSpeaker(session1.id(), speaker1.id()).toBlocking().first()
    sessionDao.addSpeaker(session1.id(), speaker2.id()).toBlocking().first()


    sessionDao.insertOrUpdate(session1.id(), session1.title(), session1.description(),
        session1.tags(),
        session1.locationId(), session1.startTime(), session1.endTime(), true).toBlocking().first()

    val queried = sessionDao.getSessions().toBlocking().first()
    assertEquals(1, queried.size)
    assertEquals(listOf(session1), queried)

    // insert second session
    sessionDao.insertOrUpdate(session2.id(), session2.title(), session2.description(),
        session2.tags(),
        session2.locationId(), session2.startTime(), session2.endTime(), true).toBlocking().first()
    sessionDao.addSpeaker(session2.id(), speaker2.id()).toBlocking().first()

    val queriedTwoSessions = sessionDao.getSessions().toBlocking().first()
    assertEquals(2, queriedTwoSessions.size)
    assertEquals(listOf(session1, session2), queriedTwoSessions)


    // remove location --> should delete trigger to set null
    locationDao.remove(locationId1).toBlocking().first()

    val queriedWithoutLocation = sessionDao.getSessions().toBlocking().first()
    val session1WithoutLocation = SessionAutoValue.create("1", "Title1", "Description1", "tags1",
        session1.startTime(), session1.endTime(), null, null, true, listOf(speaker1, speaker2))
    assertEquals(2, queriedWithoutLocation.size)
    assertEquals(session1WithoutLocation, queriedWithoutLocation[0])


    // remove speaker1 --> should trigger delete on cascade
    speakerDao.remove(speaker1.id()).toBlocking().first()

    val queriedTwoSessionsWithoutSpeaker1 = sessionDao.getSessions().toBlocking().first()
    val session1WithoutLocationWithoutSpeaker1 = SessionAutoValue.create("1", "Title1",
        "Description1",
        "tags1",
        session1.startTime(), session1.endTime(), null, null, true, listOf(speaker2))

    val session2WithoutLocation = SessionAutoValue.create(session2.id(), session2.title(),
        session2.description(), session2.tags(), session2.startTime(), session2.endTime(),
        null, null, true, listOf(speaker2))

    assertEquals(2, queriedTwoSessionsWithoutSpeaker1.size)
    assertEquals(listOf(session1WithoutLocationWithoutSpeaker1, session2WithoutLocation),
        queriedTwoSessionsWithoutSpeaker1)


    // Remove speaker from session
    sessionDao.removeSpeaker(session2.id(), speaker2.id()).toBlocking().first()

    val queryWithoutSpeaker1Speaker2Location1 = sessionDao.getSessions().toBlocking().first()

    val session2WithoutLocationWithoutSpeaker2 = SessionAutoValue.create(session2.id(),
        session2.title(),
        session2.description(), session2.tags(), session2.startTime(), session2.endTime(),
        null, null, true, listOf())

    assertEquals(2, queryWithoutSpeaker1Speaker2Location1.size)
    assertEquals(
        listOf(session1WithoutLocationWithoutSpeaker1, session2WithoutLocationWithoutSpeaker2),
        queryWithoutSpeaker1Speaker2Location1)


    // delete session
    sessionDao.remove(session1.id()).toBlocking().first()

    val querySession2 = sessionDao.getSessions().toBlocking().first()
    assertEquals(1, querySession2.size)
    assertEquals(listOf(session2WithoutLocationWithoutSpeaker2), querySession2)

  }

  @Test
  fun getByIdAndRemove() {

    val locationId1 = "loc1"
    val locationName1 = "location1"

    val speaker1 = SpeakerAutoValue.create("sp1", "spname1", null, "pic1", null, null, null, null,
        null)
    val speaker2 = SpeakerAutoValue.create("sp2", "spname2", null, null, null, null, null, null,
        null)

    val session1 = SessionAutoValue.create("1", "Title1", "Description1", "tags1", Instant.now(),
        Instant.now(), locationId1, locationName1, true, listOf(speaker1, speaker2))

    val session2 = SessionAutoValue.create("2", "Title2", "Description2", "tags2", Instant.now(),
        Instant.now(), locationId1, locationName1, true, listOf(speaker2))


    locationDao.insertOrUpdate(locationId1, locationName1).toBlocking().first()
    speakerDao.insertOrUpdate(speaker1.id(), speaker1.name(), speaker1.info(),
        speaker1.profilePic(), speaker1.company(), speaker1.jobTitle(), speaker1.link1(),
        speaker1.link2(), speaker1.link3()).toBlocking().first()

    speakerDao.insertOrUpdate(speaker2.id(), speaker2.name(), speaker2.info(),
        speaker2.profilePic(), speaker2.company(), speaker2.jobTitle(), speaker2.link1(),
        speaker2.link2(), speaker2.link3()).toBlocking().first()

    sessionDao.addSpeaker(session1.id(), speaker1.id()).toBlocking().first()
    sessionDao.addSpeaker(session1.id(), speaker2.id()).toBlocking().first()


    sessionDao.insertOrUpdate(session1.id(), session1.title(), session1.description(),
        session1.tags(),
        session1.locationId(), session1.startTime(), session1.endTime(), true).toBlocking().first()

    sessionDao.insertOrUpdate(session2.id(), session2.title(), session2.description(),
        session2.tags(),
        session2.locationId(), session2.startTime(), session2.endTime(), false).toBlocking().first()


    val all = sessionDao.getSessions().toBlocking().first()
    assertEquals(2, all.size)

    val queriedSession1 = sessionDao.getById(session1.id()).toBlocking().first();
    assertEquals(queriedSession1, session1)


    val deleted = sessionDao.remove(session1.id()).toBlocking().first()
    assertEquals(1, deleted)

    assertNull(sessionDao.getById(session1.id()).toBlocking().first())
  }

  @Test
  fun favorites() {

    val locationId1 = "loc1"
    val locationName1 = "location1"

    val speaker1 = SpeakerAutoValue.create("sp1", "spname1", null, "pic1", null, null, null, null,
        null)
    val speaker2 = SpeakerAutoValue.create("sp2", "spname2", null, null, null, null, null, null,
        null)

    val session1 = SessionAutoValue.create("1", "Title1", "Description1", "tags1", Instant.now(),
        Instant.now(), locationId1, locationName1, true, listOf(speaker1, speaker2))

    val session2 = SessionAutoValue.create("2", "Title2", "Description2", "tags2", Instant.now(),
        Instant.now(), locationId1, locationName1, false, listOf(speaker2))


    locationDao.insertOrUpdate(locationId1, locationName1).toBlocking().first()
    speakerDao.insertOrUpdate(speaker1.id(), speaker1.name(), speaker1.info(),
        speaker1.profilePic(), speaker1.company(), speaker1.jobTitle(), speaker1.link1(),
        speaker1.link2(), speaker1.link3()).toBlocking().first()

    speakerDao.insertOrUpdate(speaker2.id(), speaker2.name(), speaker2.info(),
        speaker2.profilePic(), speaker2.company(), speaker2.jobTitle(), speaker2.link1(),
        speaker2.link2(), speaker2.link3()).toBlocking().first()

    sessionDao.addSpeaker(session1.id(), speaker1.id()).toBlocking().first()
    sessionDao.addSpeaker(session1.id(), speaker2.id()).toBlocking().first()


    sessionDao.insertOrUpdate(session1.id(), session1.title(), session1.description(),
        session1.tags(),
        session1.locationId(), session1.startTime(), session1.endTime(), true).toBlocking().first()

    sessionDao.insertOrUpdate(session2.id(), session2.title(), session2.description(),
        session2.tags(),
        session2.locationId(), session2.startTime(), session2.endTime(), false).toBlocking().first()


    val favorites = sessionDao.getFavoriteSessions().toBlocking().first()
    assertEquals(listOf(session1), favorites)


    assertEquals(1, sessionDao.setFavorite(session1.id(), false).toBlocking().first())
    val favorites2 = sessionDao.getFavoriteSessions().toBlocking().first()
    assertTrue(favorites2.isEmpty())

    sessionDao.setFavorite(session1.id(), true).toBlocking().first()
    assertEquals(listOf(session1), sessionDao.getFavoriteSessions().toBlocking().first())


  }
}
