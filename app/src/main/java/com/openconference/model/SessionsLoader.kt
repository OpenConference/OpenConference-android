package com.openconference.model

import com.openconference.model.database.dao.SessionDao
import com.openconference.model.notification.NotificationScheduler
import rx.Observable

/**
 * Responsible to load all sessions
 *
 * @author Hannes Dorfmann
 */
interface SessionsLoader {

  /**
   * Load all sessions
   */
  fun allSessions(): Observable<List<Session>>

  fun favoriteSessions(): Observable<List<Session>>

  fun getSession(id: String): Observable<Session>

  fun addSessionToSchedule(session: Session): Observable<Boolean>

  fun removeSessionFromSchedule(session: Session): Observable<Boolean>

  fun getSessionsOfSpeaker(speakerId: String): Observable<List<Session>>
}

/**
 * A [SessionsLoader] that uses the sessions from local database (synced with the backend)
 */
class LocalStorageSessionsLoader(private val scheduleDataAwareObservableFactory: ScheduleDataAwareObservableFactory,
    private val sessionDao: SessionDao,
    private val notificationScheduler: NotificationScheduler
) : SessionsLoader {

  override fun allSessions(): Observable<List<Session>> =
      scheduleDataAwareObservableFactory.create(sessionDao.getSessions())

  override fun favoriteSessions(): Observable<List<Session>> = scheduleDataAwareObservableFactory.create(
      sessionDao.getFavoriteSessions())

  override fun getSession(
      id: String): Observable<Session> = scheduleDataAwareObservableFactory.create(
      sessionDao.getById(id))

  override fun addSessionToSchedule(session: Session): Observable<Boolean> = sessionDao.setFavorite(
      session.id(),
      true).map { it > 0 }
      .doOnNext { notificationScheduler.addOrRescheduleNotification(session) }

  override fun removeSessionFromSchedule(
      session: Session): Observable<Boolean> = sessionDao.setFavorite(
      session.id(),
      false).map { it > 0 }
      .doOnNext { notificationScheduler.removeNotification(session) }

  override fun getSessionsOfSpeaker(speakerId: String): Observable<List<Session>> =
      scheduleDataAwareObservableFactory.create(sessionDao.getSessionsOfSpeaker(speakerId))
}