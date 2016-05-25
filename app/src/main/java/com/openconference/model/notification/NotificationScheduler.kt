package com.openconference.model.notification

import com.openconference.model.Session

/**
 * Responsible to interact with the android systems AlarmManager to schedule Notifications
 * to be shown before the session starts.
 *
 * @author Hannes Dorfmann
 */
interface NotificationScheduler {

  /**
   * Remove any scheduled notification for a given session.
   * Does nothing if the no notification for this session has been scheduled before
   */
  fun removeNotification(session: Session)

  /**
   * Schedule a notification for the given session or reschedule an existing scheduled
   * notification for the same session
   */
  fun addOrRescheduleNotification(session: Session)
}