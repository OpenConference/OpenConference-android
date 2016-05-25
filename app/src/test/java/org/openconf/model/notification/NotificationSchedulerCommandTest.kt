package org.openconf.model.notification

import org.openconf.model.Session
import org.junit.Test
import org.mockito.Mockito

/**
 *
 *
 * @author Hannes Dorfmann
 */
class  NotificationSchedulerCommandTest {

  @Test
  fun removeScheduledNotification() {
    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)
    val session = Mockito.mock(Session::class.java)

    val command = RemoveScheduledNotificationCommand(session, notificationScheduler)
    command.execute()

    Mockito.verify(notificationScheduler, Mockito.only()).removeNotification(session)
  }


  @Test
  fun addOrRescheduleNotification() {
    val notificationScheduler = Mockito.mock(NotificationScheduler::class.java)
    val session = Mockito.mock(Session::class.java)

    val command = AddOrRescheduleNotificationCommand(session, notificationScheduler)
    command.execute()

    Mockito.verify(notificationScheduler, Mockito.only()).addOrRescheduleNotification(session)
  }
}