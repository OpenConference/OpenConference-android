package com.openconference.model.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.openconference.model.Session
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import timber.log.Timber

/**
 * Default [NotificationScheduler] for android
 *
 * @author Hannes Dorfmann
 */
class DefaultNotificationScheduler(private val context: Context) : NotificationScheduler {

  companion object {
    // TODO add settings
    val NOTIFICTAION_BEFORE_SESSION_START = 10 * 60 * 1000L // 10 Minutes in millis
  }


  private inline fun alarmService(): AlarmManager =
      context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

  inline private fun buildPendingIntent(s: Session): PendingIntent {
    val intent = Intent(context, NotificationBroadcastReceiver::class.java)
    intent.putExtra(NotificationBroadcastReceiver.SESSION_ID, s.id())
    return PendingIntent.getBroadcast(context, 0, intent, 0)
  }

  override fun removeNotification(session: Session) {
    alarmService().cancel(buildPendingIntent(session))
  }

  override fun addOrRescheduleNotification(session: Session) {

    removeNotification(session)

    val startTime = session.startTime() ?: return

    val intent = buildPendingIntent(session)
    val notificationTime = if (Instant.now().plusMillis(NOTIFICTAION_BEFORE_SESSION_START).isAfter(
        startTime)) Instant.now().plusSeconds(2)
    else startTime.minusMillis(
        NOTIFICTAION_BEFORE_SESSION_START)

    val localTime = LocalDateTime.ofInstant(notificationTime, ZoneId.systemDefault())
    Timber.d("Schedule notification at $localTime  " + LocalDateTime.now(ZoneId.systemDefault()))

    if (Build.VERSION.SDK_INT < 23) {
      alarmService().setExact(AlarmManager.RTC_WAKEUP,
          notificationTime.toEpochMilli(), intent)
    } else {
      alarmService().setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
          notificationTime.toEpochMilli(), intent)
    }
  }
}