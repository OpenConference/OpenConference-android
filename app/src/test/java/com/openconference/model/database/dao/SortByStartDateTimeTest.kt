package com.openconference.model.database.dao

import com.openconference.model.database.SessionAutoValue
import com.openconference.model.database.SessionDateTimeComparator
import org.junit.Test
import org.threeten.bp.Instant
import kotlin.test.assertEquals

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SortByStartDateTimeTest {

  private val sortByStartDate = SessionDateTimeComparator()

  @Test
  fun bothDatesNull() {

    val a = SessionAutoValue.create("a", null, null, null, null, null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, null, null, null, null, false,
        emptyList())

    assertEquals(0, sortByStartDate.compare(a, b))
  }

  @Test
  fun aNull() {

    val a = SessionAutoValue.create("a", null, null, null, null, null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, Instant.now(), null, null, null, false,
        emptyList())

    assertEquals(1, sortByStartDate.compare(a, b))
  }

  @Test
  fun bNull() {

    val a = SessionAutoValue.create("a", null, null, null, Instant.now(), null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, null, null, null, null, false,
        emptyList())

    assertEquals(-1, sortByStartDate.compare(a, b))
  }

  @Test
  fun compareTimes() {

    val a = SessionAutoValue.create("a", null, null, null, Instant.now(), null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, Instant.now().plusSeconds(10), null, null, null, false,
        emptyList())

    assertEquals(-1, sortByStartDate.compare(a, b))
  }


  @Test
  fun compareTimesReverse() {

    val a = SessionAutoValue.create("a", null, null, null, Instant.now().plusSeconds(10), null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, Instant.now(), null, null, null, false,
        emptyList())

    assertEquals(1, sortByStartDate.compare(a, b))
  }


  @Test
  fun example(){
    val a = SessionAutoValue.create("a", null, null, null, null, null, null, null, false,
        emptyList())

    val b = SessionAutoValue.create("b", null, null, null, Instant.now().plusSeconds(10), null, null, null, false,
        emptyList())

    val c = SessionAutoValue.create("c", null, null, null, Instant.now(), null, null, null, false,
        emptyList())


    val list = listOf(a,b,c)

    val sorted = list.sortedWith(sortByStartDate)

    assertEquals(listOf(c, b, a), sorted)
  }
}