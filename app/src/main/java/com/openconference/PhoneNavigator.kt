package com.openconference

import android.app.Activity
import android.content.Intent
import com.openconference.main.ViewPagerMainActivity
import com.openconference.model.Session
import com.openconference.model.Speaker
import com.openconference.model.screen.SessionsScreen
import com.openconference.search.SearchActivity
import com.openconference.sessiondetails.SessionDetailsActivity
import com.openconference.sessiondetails.SpeakerDetailsActivity

/**
 * A Navigator that is responsible to navigate between the different screens
 *
 * @author Hannes Dorfmann
 */
// TODO Tablet Navigator
class PhoneNavigator(private val activity: Activity) : Navigator {

  override fun showSessionDetails(session: Session) {
    val i = SessionDetailsActivity.buildIntent(activity, session)
    activity.startActivity(i)
  }

  override fun showMySchedule() {
    throw UnsupportedOperationException()
  }

  override fun showSpeakerDetails(speaker: Speaker) {
    val i = Intent(activity, SpeakerDetailsActivity::class.java)
    i.putExtra(SpeakerDetailsActivity.KEY_SPEAKER, speaker)
    activity.startActivity(i)
  }

  override fun showSessions() = if (activity is ViewPagerMainActivity) {
    activity.jumpToScreen({ it is SessionsScreen })
  } else throw UnsupportedOperationException("Oops, something in Navigation is not setup properly")

  override fun showSpeakers() {
    throw UnsupportedOperationException()
  }

  override fun showSearch() {
    val i = Intent(activity, SearchActivity::class.java)
    activity.startActivity(i)
    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
  }
}