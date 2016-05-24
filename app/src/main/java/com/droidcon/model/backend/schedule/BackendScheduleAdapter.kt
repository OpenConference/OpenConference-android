package com.droidcon.model.backend.schedule

import com.droidcon.model.Location
import com.droidcon.model.Session
import com.droidcon.model.Speaker
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