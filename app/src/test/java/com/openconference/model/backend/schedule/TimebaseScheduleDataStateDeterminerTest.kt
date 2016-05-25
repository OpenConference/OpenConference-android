package com.openconference.model.backend.schedule

import android.content.SharedPreferences
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.threeten.bp.Instant
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 *
 *
 * @author Hannes Dorfmann
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(TimebaseScheduleDataStateDeterminer::class)
class TimebaseScheduleDataStateDeterminerTest {

  @Test
  fun neverRanBefore() {
    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val determiner = PowerMockito.spy(TimebaseScheduleDataStateDeterminer(sharedPrefs, 10))

    Mockito.doReturn(false).`when`(sharedPrefs).getBoolean(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_RUN_AT_LEAST_ONCE), Mockito.eq(false))

    var now = Instant.now()

    Mockito.doReturn(now).`when`(determiner).currentTime()

    assertEquals(
        ScheduleDataStateDeterminer.ScheduleDataState.NO_DATA,
        determiner.getScheduleSyncDataState().toBlocking().first()
    )
  }

  @Test
  fun upToDate() {
    val expiresAfter = 10L
    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val determiner = PowerMockito.spy(
        TimebaseScheduleDataStateDeterminer(sharedPrefs, expiresAfter))

    Mockito.doReturn(true).`when`(sharedPrefs).getBoolean(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_RUN_AT_LEAST_ONCE), Mockito.eq(false))

    val now = Instant.now()

    Mockito.doReturn(now).`when`(determiner).currentTime()
    Mockito.doReturn(now.minusMillis(expiresAfter - 1).toEpochMilli()).`when`(sharedPrefs).getLong(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_LAST_SYNC), Mockito.eq(0L))

    assertEquals(
        ScheduleDataStateDeterminer.ScheduleDataState.UP_TO_DATE,
        determiner.getScheduleSyncDataState().toBlocking().first()
    )
  }

  @Test
  fun syncBackground() {
    val expiresAfter = 10L
    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val determiner = PowerMockito.spy(
        TimebaseScheduleDataStateDeterminer(sharedPrefs, expiresAfter))

    Mockito.doReturn(true).`when`(sharedPrefs).getBoolean(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_RUN_AT_LEAST_ONCE), Mockito.eq(false))

    val now = Instant.now()

    Mockito.doReturn(now).`when`(determiner).currentTime()
    Mockito.doReturn(now.minusMillis(expiresAfter).toEpochMilli()).`when`(sharedPrefs).getLong(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_LAST_SYNC), Mockito.eq(0L))

    assertEquals(
        ScheduleDataStateDeterminer.ScheduleDataState.RUN_BACKGROUND_SYNC,
        determiner.getScheduleSyncDataState().toBlocking().first()
    )
  }

  @Test
  fun markScheduleSyncedSuccessful() {
    val expiresAfter = 10L
    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val determiner = PowerMockito.spy(
        TimebaseScheduleDataStateDeterminer(sharedPrefs, expiresAfter))

    val editor = Mockito.mock(SharedPreferences.Editor::class.java)
    val now = Instant.now()

    Mockito.doReturn(editor).`when`(sharedPrefs).edit()
    Mockito.doReturn(now).`when`(determiner).currentTime()
    Mockito.doReturn(true).`when`(editor).commit()

    Mockito.doReturn(editor).`when`(editor).putBoolean(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_RUN_AT_LEAST_ONCE), Mockito.eq(true))
    Mockito.doReturn(editor).`when`(editor).putLong(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_LAST_SYNC),
        Mockito.eq(now.toEpochMilli()))

    assertTrue (determiner.markScheduleSyncedSuccessful().toBlocking().first())

    Mockito.verify(editor, Mockito.times(1)).putBoolean(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_RUN_AT_LEAST_ONCE), Mockito.eq(true))
    Mockito.verify(editor, Mockito.times(1)).putLong(
        Mockito.eq(TimebaseScheduleDataStateDeterminer.KEY_LAST_SYNC),
        Mockito.eq(now.toEpochMilli()))
    Mockito.verify(editor, Mockito.times(1)).commit()

    Mockito.verifyNoMoreInteractions(editor)


  }

}