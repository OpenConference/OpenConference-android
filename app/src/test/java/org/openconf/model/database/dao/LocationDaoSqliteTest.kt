package org.openconf.model.database.dao

import org.openconf.BuildConfig
import org.openconf.model.database.LocationAutoValue
import com.hannesdorfmann.sqlbrite.dao.Dao
import com.hannesdorfmann.sqlbrite.dao.DaoManager
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
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class LocationDaoSqliteTest {

  lateinit var dao: LocationDao
  lateinit var manager: DaoManager
  var initialized = false;

  @Before
  fun setup() {
    if (!initialized) {
      initialized = true
      dao = LocationDaoSqlite()
      manager = DaoManager.with(RuntimeEnvironment.application)
          .add(dao as Dao)
          .databaseName("location-test")
          .version(1)
          .build()
    }
  }

  @Test
  fun insertAndGetAllAndRemove() {


    assertTrue(dao.getLocations().toBlocking().first().isEmpty())
    dao.insertOrUpdate("1", "name1").toBlocking().first()

    var locations = dao.getLocations().toBlocking().first()
    assertTrue(locations.size == 1)

    assertEquals(LocationAutoValue.create("1", "name1"), locations[0])


    dao.insertOrUpdate("2", "name2").toBlocking().first()

    locations = dao.getLocations().toBlocking().first()
    assertEquals(2, locations.size)
    assertEquals(LocationAutoValue.create("1", "name1"), locations[0])
    assertEquals(LocationAutoValue.create("2", "name2"), locations[1])


    // Remove
    val removedCount = dao.remove("1").toBlocking().first()
    assertEquals(1, removedCount)

    locations = dao.getLocations().toBlocking().first()
    assertEquals(1, locations.size)
    assertEquals(LocationAutoValue.create("2", "name2"), locations[0])

    // Update
    dao.insertOrUpdate("2", "newName2").toBlocking().first()
    locations = dao.getLocations().toBlocking().first()
    assertEquals(1, locations.size)
    assertEquals(LocationAutoValue.create("2", "newName2"), locations[0])


    // Remove all
    dao.removeAll().toBlocking().first()
    locations = dao.getLocations().toBlocking().first()
    assertTrue(locations.isEmpty())
  }
}
