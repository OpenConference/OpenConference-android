package org.openconf.model.backend.schedule

import org.openconf.model.Location
import org.openconf.model.Session
import org.openconf.model.Speaker
import rx.Observable

/**
 * API to communicate with the backend. This is where you have to communicate with
 * your conferences backend
 * @author Hannes Dorfmann
 */
interface BackendScheduleAdapter {

  /**
   * Get a list of all Speakers
   */
  fun getSpeakers(): Observable<BackendScheduleResponse<Speaker>>

  /**
   * Get a list of all locations where sessions will be heldt
   */
  fun getLocations(): Observable<BackendScheduleResponse<Location>>

  /**
   * Get list of all sessions
   */
  fun getSessions(): Observable<BackendScheduleResponse<Session>>
}