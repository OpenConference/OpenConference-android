package com.openconference.model

import com.openconference.model.database.dao.SpeakerDao
import rx.Observable

/**
 * Responsible to load Speakers
 *
 * @author Hannes Dorfmann
 */
interface SpeakersLoader {

  /**
   * Get a list of all speakers
   */
  fun allSpeakers(): Observable<List<Speaker>>
}

/**
 * A [SpeakersLoader] that uses the speakers from local database
 */
class LocalStorageSpeakersLoader(private val scheduleDataAwareObservableFactory: ScheduleDataAwareObservableFactory, private val speakerDao: SpeakerDao) : SpeakersLoader {

  override fun allSpeakers(): Observable<List<Speaker>> = scheduleDataAwareObservableFactory.create(
      speakerDao.getSpeakers())
}