package com.openconference.model.database.dao

import android.database.Cursor
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.gabrielittner.auto.value.cursor.ColumnName
import com.gabrielittner.auto.value.cursor.CursorAdapter
import com.google.auto.value.AutoValue
import com.openconference.model.Session
import com.openconference.model.database.*
import com.ryanharter.auto.value.parcel.ParcelAdapter
import org.threeten.bp.Instant
import rx.functions.Func1

/**
 * A little helper class used to retrieve the join result (session, location, speaker)
 *
 * @author Hannes Dorfmann
 */
@AutoValue
abstract class SessionJoinResult {

  /**
   * The session id
   */
  @NonNull
  @ColumnName(SessionDaoSqlite.COL_ID)
  abstract fun id(): String

  /**
   * The title of the sessions
   */
  @Nullable
  @ColumnName(SessionDaoSqlite.COL_TITLE)
  abstract fun title(): String?

  /**
   * The description of the speaker
   */
  @Nullable
  @ColumnName(SessionDaoSqlite.COL_DESCRIPTION)
  abstract fun description(): String?

  /**
   * Optional tags for this session
   */
  @Nullable
  @ColumnName(SessionDaoSqlite.COL_TAGS)
  abstract fun tags(): String?

  /**
   * Start date / time
   */
  @Nullable
  @ParcelAdapter(InstantParcelableTypeAdapter::class)
  @CursorAdapter(StartTimeInstantCursorAdapter::class)
  abstract fun startTime(): Instant?

  /**
   * End date / time
   */
  @Nullable
  @ParcelAdapter(InstantParcelableTypeAdapter::class)
  @CursorAdapter(EndTimeInstantCursorAdapter::class)
  abstract fun endTime(): Instant?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_LOCATION_ID)
  abstract fun locationId(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_LOCATION_NAME)
  abstract fun locationName(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_SPEAKER_ID)
  abstract fun speakerId(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_SPEAKER_NAME)
  abstract fun speakerName(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_SPEAKER_PIC)
  abstract fun speakerPicture(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_SPEAKER_COMPANY)
  abstract fun speakerCompany(): String?

  @Nullable
  @ColumnName(SessionDaoSqlite.COL_SPEAKER_JOBTITLE)
  abstract fun speakerJobTitle(): String?

  @NonNull
  @ColumnName(SessionDaoSqlite.COL_FAVORITE)
  abstract fun favorite(): Boolean


  companion object {
    @JvmStatic
    fun mapper(): Func1<Cursor, SessionJoinResult> = AutoValue_SessionJoinResult.MAPPER
  }

}


fun mapJoinResultToSessions(joinResults: List<SessionJoinResult>): List<Session>
    =
    joinResults
        .groupBy { it.id() }
        .map { it.value }
        .map {
          val first = it[0]

          val speakers: List<SpeakerAutoValue> = it.filter { it.speakerId() != null && it.speakerName() != null }
              .map {
                SpeakerAutoValue.create(
                    it.speakerId()!!,
                    it.speakerName()!!,
                    null,
                    it.speakerPicture(),
                    it.speakerCompany(),
                    it.speakerJobTitle(),
                    null,
                    null,
                    null)
              }

          SessionAutoValue.create(first.id(),
              first.title(),
              first.description(),
              first.tags(),
              first.startTime(),
              first.endTime(),
              first.locationId(),
              first.locationName(),
              first.favorite(),
              speakers)
        }
