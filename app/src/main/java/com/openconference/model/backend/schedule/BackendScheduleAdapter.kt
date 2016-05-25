package com.openconference.model.backend.schedule

import com.openconference.model.Location
import com.openconference.model.Session
import com.openconference.model.Speaker
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