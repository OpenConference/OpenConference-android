package com.droidcon.model

import com.droidcon.model.backend.schedule.ScheduleDataStateDeterminer
import com.droidcon.model.backend.schedule.ScheduleSync
import rx.Observable
import rx.Scheduler

/**
 * This is a Factory that creates an rx Observable that before running checks if the schedule data
 * (sessions, speaker etc.) is available / up-to-date on the local device.
 * If not [ScheduleSync] will be started.
 *
 * @author Hannes Dorfmann
 */
class ScheduleDataAwareObservableFactory(private val scheduleSync: ScheduleSync, private val scheduleDataStateDeterminer: ScheduleDataStateDeterminer, private val backgroundSyncScheduler: Scheduler) {

  /**
   * Creates an Observable that is aware of the schedule data on the users device.
   * Depending on the state of the schedule data observables created by this method
   * may wait until a full sync has been finished (i.e. in case no data was on users device before)
   * before resuming with the original observable, or maybe a background sync will be started
   * (not waiting to complete)
   */
  fun <T> create(originalObservable: Observable<T>): Observable<T> =
      scheduleDataStateDeterminer.getScheduleSyncDataState().flatMap {
        when (it) {
          ScheduleDataStateDeterminer.ScheduleDataState.UP_TO_DATE -> originalObservable

          ScheduleDataStateDeterminer.ScheduleDataState.NO_DATA ->
            scheduleSync.executeSync()
                .flatMap { scheduleDataStateDeterminer.markScheduleSyncedSuccessful() }
                .flatMap { originalObservable }

          ScheduleDataStateDeterminer.ScheduleDataState.RUN_BACKGROUND_SYNC -> {
            // Execute sync in background
            scheduleSync.executeSync()
                .flatMap { scheduleDataStateDeterminer.markScheduleSyncedSuccessful() }
                .observeOn(backgroundSyncScheduler)
                .subscribeOn(backgroundSyncScheduler)
                .subscribe()

            // Continue with original observable in parallel
            originalObservable
          }
        }
      }

}