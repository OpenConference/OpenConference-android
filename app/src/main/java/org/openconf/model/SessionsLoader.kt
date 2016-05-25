package org.openconf.model

import org.openconf.model.database.dao.SessionDao
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
}

/**
 * A [SessionsLoader] that uses the sessions from local database (synced with the backend)
 */
class LocalStorageSessionsLoader(private val scheduleDataAwareObservableFactory: ScheduleDataAwareObservableFactory, private val sessionDao: SessionDao) : SessionsLoader {

  override fun allSessions(): Observable<List<Session>> =
      scheduleDataAwareObservableFactory.create(sessionDao.getSessions())

}