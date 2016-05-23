package com.droidcon.model.backend.schedule

import android.content.SharedPreferences
import org.threeten.bp.Instant
import rx.Observable

/**
 * This [TimebaseScheduleDataStateDeterminer] basically says that every two hours a sync will run in background
 * @author Hannes Dorfmann
 */
class TimebaseScheduleDataStateDeterminer(private val sharedPrefs: SharedPreferences, private val runAfter: Long = 2 * 3600 * 1000) : ScheduleDataStateDeterminer {

  companion object {
    val KEY_LAST_SYNC = "lastSync"
    val KEY_RUN_AT_LEAST_ONCE = "atLeastOnce"
  }

  override fun getScheduleSyncDataState(): Observable<ScheduleDataStateDeterminer.ScheduleDataState> =
      Observable.fromCallable {
        val atLeastOnce = sharedPrefs.getBoolean(KEY_RUN_AT_LEAST_ONCE, false)

        if (!atLeastOnce) {
          ScheduleDataStateDeterminer.ScheduleDataState.NO_DATA
        } else {
          val lastSyncMS = sharedPrefs.getLong(KEY_LAST_SYNC, 0)
          val lastSync = Instant.ofEpochMilli(lastSyncMS)
          val currentTime = currentTime()
          val expiresAt = lastSync.plusMillis(runAfter)
          if (expiresAt.isAfter(currentTime)) {
            ScheduleDataStateDeterminer.ScheduleDataState.UP_TO_DATE
          } else {
            ScheduleDataStateDeterminer.ScheduleDataState.RUN_BACKGROUND_SYNC
          }
        }

      }

  override fun markScheduleSyncedSuccessful(): Observable<Boolean> =
      Observable.fromCallable {
        sharedPrefs.edit()
            .putBoolean(KEY_RUN_AT_LEAST_ONCE, true)
            .putLong(KEY_LAST_SYNC, currentTime().toEpochMilli())
            .commit()
      }

  fun currentTime() = Instant.now()
}