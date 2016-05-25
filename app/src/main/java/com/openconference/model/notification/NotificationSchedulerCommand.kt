package com.openconference.model.notification

import com.openconference.model.notification.NotificationScheduler
import com.openconference.model.Session

/**
 * Implementation of the command pattern
 *
 * @author Hannes Dorfmann
 */
interface NotificationSchedulerCommand {

  /**
   * Executes the command
   */
  fun execute()
}

/**
 * Removes a Scheduled Notification for the given session
 */
class RemoveScheduledNotificationCommand(private val session: Session, private val notificationScheduler: NotificationScheduler) : NotificationSchedulerCommand {

  override fun execute() {
    notificationScheduler.removeNotification(session)
  }
}

/**
 * Adds or reschedule a Notification for the given Session
 */
class AddOrRescheduleNotificationCommand(private val session: Session, private val notificationScheduler: NotificationScheduler) : NotificationSchedulerCommand {

  override fun execute() {
    notificationScheduler.addOrRescheduleNotification(session)
  }
}