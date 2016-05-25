package org.openconf.model.backend.schedule

import rx.Observable

/**
 * Determine the state of the schedule.
 *
 * @author Hannes Dorfmann
 */
interface ScheduleDataStateDeterminer {

  /**
   * Enum representing whether or not [ScheduleSync] should running or not
   */
  enum class ScheduleDataState {

    /**
     * Syncing has to be executed right now, before continue with the rest of your data flow
     */
    NO_DATA,
    /**
     * Schedule data is available. That data might not be up-to-date (stale data) but can be used
     * in the mean time i.e. to display the schedule in the UI, while starting a refresh in the
     * background
     */
    RUN_BACKGROUND_SYNC,

    /**
     * Schedule data is up to date. Running sync is not needed.
     */
    UP_TO_DATE
  }


  /**
   * Determine the current state of the schedule sync data
   */
  fun getScheduleSyncDataState(): Observable<ScheduleDataState>

  /**
   * Marks that the schedule has been run successfully
   */
  fun markScheduleSyncedSuccessful(): Observable<Boolean>

}