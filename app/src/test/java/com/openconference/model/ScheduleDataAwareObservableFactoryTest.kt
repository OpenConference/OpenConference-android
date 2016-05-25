package com.openconference.model

import com.openconference.model.backend.schedule.ScheduleDataStateDeterminer
import com.openconference.model.backend.schedule.ScheduleSync
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import rx.Observable
import rx.schedulers.Schedulers
import kotlin.test.assertTrue

/**
 *
 *
 * @author Hannes Dorfmann
 */
@RunWith(PowerMockRunner::class)
@PrepareForTest(ScheduleSync::class)
class ScheduleDataAwareObservableFactoryTest {


  @Test
  fun noData() {
    val scheduleSync = PowerMockito.mock(ScheduleSync::class.java)
    val determiner = Mockito.mock(ScheduleDataStateDeterminer::class.java)

    val dataState = Observable.fromCallable { ScheduleDataStateDeterminer.ScheduleDataState.NO_DATA }
    Mockito.doReturn(dataState).`when`(determiner).getScheduleSyncDataState()

    val markedSynced = Observable.fromCallable { true }
    Mockito.doReturn(markedSynced).`when`(determiner).markScheduleSyncedSuccessful()

    val syncSuccessful = Observable.fromCallable { true }

    Mockito.doReturn(syncSuccessful).`when`(scheduleSync).executeSync()

    val factory = ScheduleDataAwareObservableFactory(scheduleSync, determiner,
        Schedulers.immediate())

    val originalObservable = Observable.fromCallable {
      // Sync must run before original observable
      Mockito.verify(scheduleSync, Mockito.times(1)).executeSync()
      Mockito.verify(determiner, Mockito.times(1)).markScheduleSyncedSuccessful()
      true
    }

    assertTrue(factory.create(originalObservable).toBlocking().first())

  }

  @Test
  fun syncInBackground() {
    val scheduleSync = PowerMockito.mock(ScheduleSync::class.java)
    val determiner = Mockito.mock(ScheduleDataStateDeterminer::class.java)

    val dataState = Observable.fromCallable { ScheduleDataStateDeterminer.ScheduleDataState.RUN_BACKGROUND_SYNC }
    Mockito.doReturn(dataState).`when`(determiner).getScheduleSyncDataState()

    val markedSynced = Observable.fromCallable { true }
    Mockito.doReturn(markedSynced).`when`(determiner).markScheduleSyncedSuccessful()

    val syncSuccessful = Observable.fromCallable { true }

    Mockito.doReturn(syncSuccessful).`when`(scheduleSync).executeSync()

    val factory = ScheduleDataAwareObservableFactory(scheduleSync, determiner,
        Schedulers.immediate())

    var observableCalled = false
    val originalObservable = Observable.fromCallable { observableCalled = true; true }

    assertTrue(factory.create(originalObservable).toBlocking().first())


    // Sync should be run async
    Mockito.verify(scheduleSync, Mockito.times(1)).executeSync()
    Mockito.verify(determiner, Mockito.times(1)).markScheduleSyncedSuccessful()
    assertTrue(observableCalled)

  }

  @Test
  fun dataUpToDate() {
    val scheduleSync = PowerMockito.mock(ScheduleSync::class.java)
    val determiner = Mockito.mock(ScheduleDataStateDeterminer::class.java)

    val dataState = Observable.fromCallable { ScheduleDataStateDeterminer.ScheduleDataState.UP_TO_DATE }
    Mockito.doReturn(dataState).`when`(determiner).getScheduleSyncDataState()

    val markedSynced = Observable.fromCallable { true }
    Mockito.doReturn(markedSynced).`when`(determiner).markScheduleSyncedSuccessful()

    val syncSuccessful = Observable.fromCallable { true }

    Mockito.doReturn(syncSuccessful).`when`(scheduleSync).executeSync()

    val factory = ScheduleDataAwareObservableFactory(scheduleSync, determiner,
        Schedulers.io())

    var observableCalled = false
    val originalObservable = Observable.fromCallable { observableCalled = true; true }

    assertTrue(factory.create(originalObservable).toBlocking().first())


    // Sync should be run async
    Mockito.verify(scheduleSync, Mockito.never()).executeSync()
    Mockito.verify(determiner, Mockito.never()).markScheduleSyncedSuccessful()
    assertTrue(observableCalled)

  }

}