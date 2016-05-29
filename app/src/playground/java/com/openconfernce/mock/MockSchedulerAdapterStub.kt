package com.openconfernce.mock

import com.openconference.model.Location
import com.openconference.model.Session
import com.openconference.model.Speaker
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import com.openconference.model.backend.schedule.BackendScheduleResponse
import com.openconference.model.database.LocationAutoValue
import com.openconference.model.database.SessionAutoValue
import com.openconference.model.database.SpeakerAutoValue
import org.threeten.bp.Instant
import rx.Observable

/**
 *
 *
 * @author Hannes Dorfmann
 */
class MockSchedulerAdapterStub : BackendScheduleAdapter {

  //
  // Some mock speakers
  //
  private val speaker1 = SpeakerAutoValue.create("1", "John Doe",
      "John works as consultant since years", null,
      "Doe Inc.", "CEO", "http://www.joe.com", null, null)
  private val speaker2 = SpeakerAutoValue.create("2", "Larry Page", "Co Founder of Google", null,
      "Alphabet", "CEO", "http://www.google.com", null, null)

  //
  // Some mock locations
  //
  private val location1 = LocationAutoValue.create("1", "Audi Max")
  private val location2 = LocationAutoValue.create("2", "Theater 2")

  //
  // Some mock Sessions
  //
  private val session1 = SessionAutoValue.create("1", "The making of Google",
      "In this talk Larry Page will talk about the making of Google", "Founding Startup",
      Instant.now().plusSeconds(5L * 60L), Instant.now().plusSeconds(5L * 60 + 3600),
      location1.id(), location1.name(), false, arrayListOf<Speaker>(speaker2))

  override fun getSpeakers(): Observable<BackendScheduleResponse<Speaker>>
      = Observable.fromCallable {
    BackendScheduleResponse.dataChanged(arrayListOf<Speaker>(
        speaker1,
        speaker2
    ))
  }

  override fun getLocations(): Observable<BackendScheduleResponse<Location>> =
      Observable.fromCallable {
        BackendScheduleResponse.dataChanged(arrayListOf<Location>(
            location1,
            location2
        ))
      }

  override fun getSessions(): Observable<BackendScheduleResponse<Session>> =
      Observable.fromCallable {
        BackendScheduleResponse.dataChanged(arrayListOf<Session>(
            session1

            ))
      }
}