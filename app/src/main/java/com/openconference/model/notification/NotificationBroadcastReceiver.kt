package com.openconference.model.notification

import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver

/**
 *
 *
 * @author Hannes Dorfmann
 */
class NotificationBroadcastReceiver : WakefulBroadcastReceiver() {

  companion object {
    val SESSION_ID = "NotificationBroadcastReceiver.SESSION_ID"
  }

  override fun onReceive(context: Context, intent: Intent?) {

  }
}