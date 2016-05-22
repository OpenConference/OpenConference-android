package com.droidcon.model.backend.schedule

/**
 *
 * Used to determine if [ScheduleSync] should be run
 *
 * @author Hannes Dorfmann
 */
interface ScheduleChangedDetector {

  /**
   * @return true if [ScheduleSync] shout run or not
   */
  fun shouldRun(): Boolean
}