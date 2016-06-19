package com.openconference.model.database.dao

import com.hannesdorfmann.sqlbrite.dao.Dao
import com.hannesdorfmann.sqlbrite.dao.DaoManager
import com.openconference.BuildConfig
import com.openconference.TestApplication
import com.openconference.model.database.SpeakerAutoValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 *
 * @author Hannes Dorfmann
 */
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21), application = TestApplication::class)
class SpeakerDaoSqliteTest {

  lateinit var dao: SpeakerDao
  lateinit var manager: DaoManager
  var initialized = false;

  @Before
  fun setup() {
    if (!initialized) {
      initialized = true
      dao = SpeakerDaoSqlite()
      manager = DaoManager.with(RuntimeEnvironment.application)
          .add(dao as Dao)
          .databaseName("speaker-test")
          .version(1)
          .build()
    }
  }

  @Test
  fun insertAndGetAllAndRemove() {


    assertTrue(dao.getSpeakers().toBlocking().first().isEmpty())
    dao.insertOrUpdate("1", "name1", "info1", null, "company1", null, "link1", null,
        "link3").toBlocking().first()

    var speakers = dao.getSpeakers().toBlocking().first()
    assertTrue(speakers.size == 1)

    assertEquals(
        SpeakerAutoValue.create("1", "name1", "info1", null, "company1", null, "link1", null, "link3"),
        speakers[0])


    dao.insertOrUpdate("2", "name2", null, "porfilePic2", null, "jobTitle2", null, "link2",
        null).toBlocking().first()

    speakers = dao.getSpeakers().toBlocking().first()
    assertEquals(2, speakers.size)
    assertEquals(
        SpeakerAutoValue.create("1", "name1", "info1", null, "company1", null, "link1", null, "link3"),
        speakers[0])
    assertEquals(
        SpeakerAutoValue.create("2", "name2", null, "porfilePic2", null, "jobTitle2", null, "link2",
            null), speakers[1])


    // Remove
    val removedCount = dao.remove("1").toBlocking().first()
    assertEquals(1, removedCount)

    speakers = dao.getSpeakers().toBlocking().first()
    assertEquals(1, speakers.size)
    assertEquals(
        SpeakerAutoValue.create("2", "name2", null, "porfilePic2", null, "jobTitle2", null, "link2",
            null), speakers[0])


    // Update
    dao.insertOrUpdate("2", "name2", "info2", "porfilePic2", "company2", "jobTitle2", "link1",
        "link2",
        "link3").toBlocking().first()
    speakers = dao.getSpeakers().toBlocking().first()
    assertEquals(1, speakers.size)
    assertEquals(
        SpeakerAutoValue.create("2", "name2", "info2", "porfilePic2", "company2", "jobTitle2", "link1",
            "link2",
            "link3"), speakers[0])


    // Remove all
    dao.removeAll().toBlocking().first()
    speakers = dao.getSpeakers().toBlocking().first()
    assertTrue(speakers.isEmpty())
  }
}
