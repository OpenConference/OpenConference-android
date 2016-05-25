package org.openconf.model.database.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.hannesdorfmann.sqlbrite.dao.Dao
import com.squareup.sqlbrite.BriteDatabase
import org.openconf.model.Session
import org.openconf.util.putOrNull
import org.threeten.bp.Instant
import rx.Observable

/**
 * Data-Access-Object for [com.droidcon.model.Session]
 *
 * @author Hannes Dorfmann
 */
open class SessionDaoSqlite : SessionDao, Dao() {


  companion object {
    const val TABLE = "Session"
    const val COL_ID = "id"
    const val COL_TITLE = "title"
    const val COL_DESCRIPTION = "description"
    const val COL_TAGS = "tags"
    const val COL_START_TIME = "startTime"
    const val COL_END_TIME = "endTime"
    const val COL_FAVORITE = "favorite"

    const val COL_LOCATION_ID = "locationId"
    const val COL_LOCATION_NAME = "locationName"

    const val COL_SPEAKER_ID = "speakerId"
    const val COL_SPEAKER_NAME = "speakerName"
    const val COL_SPEAKER_PIC = "speakerPicture"

  }

  /**
   * Internal sql table for n:m relation between speaker and session
   */
  private object GiveTalk {
    const val TABLE = "GiveTalk"
    const val COL_SESSION_ID = "sessionId"
    const val COL_SPEAKER_ID = "speakerId"
  }

  override fun createTable(database: android.database.sqlite.SQLiteDatabase) {

    CREATE_TABLE(org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE,
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_ID} VARCHAR(40) PRIMARY KEY NOT NULL",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_TITLE} TEXT NOT NULL",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_DESCRIPTION} TEXT",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_START_TIME} BIGINTEGER",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_END_TIME} BIGINTEGER",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_TAGS} TEXT",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_LOCATION_ID} VARCHAR(20)",
        "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_FAVORITE} BOOLEAN DEFAULT FALSE",
        "FOREIGN KEY(${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_LOCATION_ID}) REFERENCES ${LocationDaoSqlite.TABLE}(${LocationDaoSqlite.COL_ID}) ON DELETE SET NULL"
    ).execute(database)

    CREATE_TABLE(org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.TABLE,
        "${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SESSION_ID} VARCHAR(40) NOT NULL",
        "${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SPEAKER_ID} VARCHAR(20) NOT NULL",
        "PRIMARY KEY(${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SESSION_ID}, ${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SPEAKER_ID})",
        "FOREIGN KEY(${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SESSION_ID}) REFERENCES ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE}(${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_ID}) ON DELETE CASCADE",
        "FOREIGN KEY(${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SPEAKER_ID}) REFERENCES ${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE}(${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.COL_ID}) ON DELETE CASCADE"
    ).execute(database)

  }

  override fun onUpgrade(db: android.database.sqlite.SQLiteDatabase, oldVersion: Int, newVersion: Int) {

  }

  private inline fun selectAll() = SELECT(
      "${org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_ID} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_ID}",
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_TITLE,
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_DESCRIPTION,
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_START_TIME,
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_END_TIME,
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_TAGS,
      org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_FAVORITE,
      "${LocationDaoSqlite.TABLE}.${LocationDaoSqlite.COL_ID} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_LOCATION_ID}",
      "${LocationDaoSqlite.TABLE}.${LocationDaoSqlite.COL_NAME} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_LOCATION_NAME}",
      "${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.COL_ID} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_SPEAKER_ID}",
      "${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.COL_NAME} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_SPEAKER_NAME}",
      "${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.COL_PICTURE} AS ${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_SPEAKER_PIC}"
  )
      .FROM(org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE)
      .LEFT_OUTER_JOIN(LocationDaoSqlite.TABLE)
      .ON("${org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_LOCATION_ID} = ${LocationDaoSqlite.TABLE}.${LocationDaoSqlite.COL_ID}")
      .LEFT_OUTER_JOIN(org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.TABLE)
      .ON("${org.openconf.model.database.dao.SessionDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SessionDaoSqlite.Companion.COL_ID} = ${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.TABLE}.${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SESSION_ID}")
      .LEFT_OUTER_JOIN(org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE)
      .ON("${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.TABLE}.${org.openconf.model.database.dao.SessionDaoSqlite.GiveTalk.COL_SPEAKER_ID} = ${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.TABLE}.${org.openconf.model.database.dao.SpeakerDaoSqlite.Companion.COL_ID}")

  override fun getSessions(): Observable<List<Session>> =
      query(selectAll())
          .run()
          .mapToList(org.openconf.model.database.dao.SessionJoinResult.Companion.mapper())
          .map(::mapJoinResultToSessions)

  override fun insertOrUpdate(id: String, title: String?, description: String?, tags: String?,
      locationId: String?, start: Instant?, end: Instant?,
      favorite: Boolean): Observable<Long> {

    val cv = ContentValues()
    cv.put(COL_ID, id)
    cv.putOrNull(COL_TITLE, title)
    cv.putOrNull(COL_DESCRIPTION, description)
    cv.putOrNull(COL_TAGS, tags)
    cv.putOrNull(COL_LOCATION_ID, locationId)
    cv.putOrNull(COL_START_TIME, start)
    cv.putOrNull(COL_END_TIME, end)
    cv.put(COL_FAVORITE, favorite)
    return insert(TABLE, cv, SQLiteDatabase.CONFLICT_REPLACE)
  }

  override fun remove(id: String): Observable<Int> = delete(TABLE, "$COL_ID = ?",
      id).flatMap { delete(GiveTalk.TABLE, "${GiveTalk.COL_SESSION_ID} = ?", id) }

  override fun removeAll(): Observable<Int> = delete(TABLE)

  override fun addSpeaker(sessionId: String, speakerId: String): Observable<Long> {
    val cv = ContentValues()
    cv.put(GiveTalk.COL_SESSION_ID, sessionId)
    cv.put(GiveTalk.COL_SPEAKER_ID, speakerId)
    return insert(GiveTalk.TABLE, cv)
  }

  override fun removeSpeaker(sessionId: String, speakerId: String): Observable<Int>
      = delete(GiveTalk.TABLE, "${GiveTalk.COL_SESSION_ID} = ? AND ${GiveTalk.COL_SPEAKER_ID} = ?",
      sessionId, speakerId)

  override fun setFavorite(sessionId: String, favorite: Boolean): Observable<Int> {
    val cv = ContentValues()
    cv.put(COL_FAVORITE, favorite)
    return update(TABLE, cv, "$COL_ID = ?", sessionId)
  }

  override fun getById(id: String): Observable<Session> =
      query(selectAll().WHERE("$TABLE.$COL_ID = ?"))
          .args(id)
          .run()
          .mapToList(SessionJoinResult.mapper())
          .map(::mapJoinResultToSessions)
          .map {
            when (it.size) {
              0 -> null
              1 -> it[0]
              else -> throw Exception("Expected a single result but got ${it.size}")
            }
          }

  override fun getFavoriteSessions(): Observable<List<Session>> =
      query(selectAll().WHERE("$COL_FAVORITE = 1"))
          .run()
          .mapToList(SessionJoinResult.mapper())
          .map(::mapJoinResultToSessions)

  override fun getBriteDatabase(): BriteDatabase = db
}