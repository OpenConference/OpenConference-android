package com.openconference

import android.app.Activity
import com.openconference.model.Session
import com.openconference.model.Speaker

/**
 * A Navigator that is responsible to navigate between the different screens
 *
 * @author Hannes Dorfmann
 */
// TODO Tablet Navigator
class PhoneNavigator(private val activity: Activity) : Navigator {

  override fun showSessionDetails(session: Session) {
    throw UnsupportedOperationException()
  }

  override fun showMySchedule() {
    throw UnsupportedOperationException()
  }

  override fun showSpeakerDetails(speaker: Speaker) {
    throw UnsupportedOperationException()
  }

  override fun showSessions() {
    throw UnsupportedOperationException()
  }

  override fun showSpeakers() {
    throw UnsupportedOperationException()
  }
}