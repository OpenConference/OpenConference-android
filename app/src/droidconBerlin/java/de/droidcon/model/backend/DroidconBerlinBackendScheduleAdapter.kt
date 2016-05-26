package de.droidcon.model.backend

import com.openconference.model.Location
import com.openconference.model.Session
import com.openconference.model.Speaker
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import com.openconference.model.backend.schedule.BackendScheduleResponse
import rx.Observable

/**
 * [BackendScheduleAdapter] for droidcon Berlin backend
 *
 * @author Hannes Dorfmann
 */
class DroidconBerlinBackendScheduleAdapter(private val backend: DroidconBerlinBackend) : BackendScheduleAdapter {

  override fun getSpeakers(): Observable<BackendScheduleResponse<Speaker>> =
      backend.getSpeakers().map {
        BackendScheduleResponse.dataChanged(it as List<Speaker>)
      }

  override fun getLocations(): Observable<BackendScheduleResponse<Location>>
      = backend.getLocations().map {
    BackendScheduleResponse.dataChanged(it as List<Location>)
  }

  override fun getSessions(): Observable<BackendScheduleResponse<Session>> =
      backend.getSessions().map {
        BackendScheduleResponse.dataChanged(it as List<Session>)
      }
}