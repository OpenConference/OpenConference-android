package com.openconference.sessiondetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.openconference.R
import com.openconference.model.Speaker

/***
 * Simple Activity that hosts a "SessionDetailsFragment"
 */
class SpeakerDetailsActivity : AppCompatActivity() {

  companion object {
    val KEY_SPEAKER = "SpeakerDetailsActivity.SPEAKER"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_session_details)

    if (savedInstanceState == null) {
      val speaker: Speaker = intent.getParcelableExtra(KEY_SPEAKER)

      supportFragmentManager.beginTransaction()
          .replace(R.id.fragmentContainer, SpeakerDetailsFragmentBuilder(speaker).build())
          .commit()
    }
  }
}
