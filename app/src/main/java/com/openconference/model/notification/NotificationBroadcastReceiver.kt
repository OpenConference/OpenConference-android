package com.openconference.model.notification

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import timber.log.Timber

/**
 *
 *
 * @author Hannes Dorfmann
 */
class NotificationBroadcastReceiver : WakefulBroadcastReceiver() {

  companion object {
    val SESSION_ID = "NotificationBroadcastReceiver.SESSION_ID"
  }

  override fun onReceive(context: Context, intent: Intent) {

    val sessionId = intent.getStringExtra(SESSION_ID)
    Timber.d("onReceive() $sessionId")

    val serviceIntent = Intent(context, NotificationBuilderIntentService::class.java)
    serviceIntent.putExtra(NotificationBuilderIntentService.KEY_SESSION_ID, sessionId)

    startWakefulService(context, serviceIntent);

  }
}