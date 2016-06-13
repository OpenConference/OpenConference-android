package com.openconference.sessiondetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openconference.R
import com.openconference.model.Session

/***
 * Simple Activity that hosts a "SessionDetailsFragment"
 */
class SessionDetailsActivity : AppCompatActivity() {

  companion object {
    val KEY_SESSION = "SessionDetailsActivity.SESSION"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_session_details)

    if (savedInstanceState == null) {
      val session: Session = intent.getParcelableExtra(KEY_SESSION)

      supportFragmentManager.beginTransaction()
          .replace(R.id.fragmentContainer, SessionDetailsFragmentBuilder(session).build())
          .commit()
    }
  }
}
