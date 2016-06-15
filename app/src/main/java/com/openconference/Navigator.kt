package com.openconference

import com.openconference.model.Session
import com.openconference.model.Speaker

/**
 *
 *
 * @author Hannes Dorfmann
 */
interface Navigator {

  fun showSessions()

  fun showSpeakers()

  fun showSessionDetails(session: Session)

  fun showMySchedule()

  fun showSpeakerDetails(speaker: Speaker)

  fun showSearch()

}