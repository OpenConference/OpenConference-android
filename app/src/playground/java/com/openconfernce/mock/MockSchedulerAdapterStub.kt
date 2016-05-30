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
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable

/**
 *
 *
 * @author Hannes Dorfmann
 */
class MockSchedulerAdapterStub : BackendScheduleAdapter {

  val formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

  //
  // Some mock speakers
  //
  private val speaker1 = SpeakerAutoValue.create("1", "John Doe",
      "John works as consultant since years", null,
      "Doe Inc.", "CEO", "http://www.joe.com", null, null)
  private val speaker2 = SpeakerAutoValue.create("2", "Larry Page", "Co Founder of Google", null,
      "Alphabet", "CEO", "http://www.google.com", null, null)
  private val speaker3 = SpeakerAutoValue.create("3", "Chet Haase", null, null,
      "Google", null, "http://www.google.com", null, null)
  private val speaker4 = SpeakerAutoValue.create("4", "Colt McAnlis", "Colt McAnlis is a Developer Advocate at Google focusing on Performance & Compression", null,
      "Google", null, "http://www.google.com", null, null)

  //
  // Some mock locations
  //
  private val location1 = LocationAutoValue.create("1", "Audi Max")
  private val location2 = LocationAutoValue.create("2", "Amphitheatre")

  //
  // Some mock Sessions
  //
  private val session1 = SessionAutoValue.create("1", "The making of Google",
      "In this talk Larry Page will talk about the making of Google", "Founding Startup",
      Instant.ofEpochSecond(1463565600L), Instant.ofEpochSecond(1463569200L),
      location1.id(), location1.name(), false, arrayListOf<Speaker>(speaker2))

  private val session2 = SessionAutoValue.create("2", "What's new in Android",
      "Come to this session to find out about new developer features in the platform. APIs, functionality, performance - it's all here.",
      "Android",
      Instant.ofEpochSecond(1463569200L), Instant.ofEpochSecond(1463572800L),
      location1.id(), location1.name(), false, arrayListOf<Speaker>(speaker3, speaker1))

  private val session3 = SessionAutoValue.create("3", "Image compression for Android developers",
      "Bloated images are slow to fetch and costly to download; which is troubling, since images comprise the largest bulk of content your app will send around. In order to keep your users happy and their bandwidth healthy, image optimization & size reduction should be on the top of your list. In this talk, we'll get into the nitty-gritty on how image compression works for your favorite file types, and show you how to use that knowledge to create smaller files for your users. This talk is applicable for any Android developer who has loaded a bitmap off a network connection.",
      "Android",
      Instant.ofEpochSecond(1463569200L), Instant.ofEpochSecond(1463572800L),
      location2.id(), location1.name(), false, arrayListOf<Speaker>(speaker2, speaker1))

  private val session4 = SessionAutoValue.create("4", "Make shinier, faster mobile games with Vulkan",
      "Vulkan is designed from the ground up for high-efficiency and unprecedented cross platform usage, introducing a paradigm shift in computer graphics. But what's really involved in adding Vulkan support to an existing code base? This talk covers both logistics and technical details of porting an OpenGL ES framework to Vulkan. In addition to that, it will also address some common post port topics on how to maximize the benefits of Vulkan.",
      "Android",
      Instant.ofEpochSecond(1463662800L), Instant.ofEpochSecond(1463666400L),
      location2.id(), location1.name(), false, arrayListOf<Speaker>(speaker1))

  private val session5 = SessionAutoValue.create("5", "Android battery and memory optimizations",
      "No one likes battery-draining apps or sluggish experience on their device. We will go over new platform features aimed at optimizing battery and memory consumption such as Doze and the new background optimizations in the N-release. We will review best practices and tools for measuring power consumption, as well as APIs that make it easy for you to write battery and memory efficient applications. Join us in helping improve battery life and performance for users.",
      "Android Battery",
      Instant.ofEpochSecond(1463670000L), Instant.ofEpochSecond(1463673600L),
      location2.id(), location1.name(), false, arrayListOf<Speaker>(speaker4))

  override fun getSpeakers(): Observable<BackendScheduleResponse<Speaker>>
      = Observable.fromCallable {
    BackendScheduleResponse.dataChanged(arrayListOf<Speaker>(
        speaker1,
        speaker2,
        speaker3,
        speaker4
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
            session1,
            session2,
            session3,
            session4,
            session5
        ))
      }
}