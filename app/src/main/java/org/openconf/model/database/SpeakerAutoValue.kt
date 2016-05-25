package org.openconf.model.database

import android.content.ContentValues
import android.database.Cursor
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import org.openconf.model.Speaker
import org.openconf.model.database.dao.SpeakerDaoSqlite
import com.gabrielittner.auto.value.cursor.ColumnName
import com.google.auto.value.AutoValue
import rx.functions.Func1

/**
 * Represents an Speaker
 * @author Hannes Dorfmann
 */
@AutoValue
abstract class SpeakerAutoValue : Speaker {

  @NonNull
  @ColumnName(SpeakerDaoSqlite.COL_ID)
  override abstract fun id(): String

  /**
   * The full name of the speaker
   */
  @NonNull
  @ColumnName(SpeakerDaoSqlite.COL_NAME)
  override abstract fun name(): String

  /**
   * The bio / info about the speaker
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_INFO)
  override abstract fun info(): String ?

  /**
   * The company's name the speaker is working for
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_COMPANY)
  override abstract fun company(): String?

  /**
   * The job title / role in the company
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_JOB_TITLE)
  override abstract fun jobTitle(): String?

  /**
   * The first link i.e. link to twitter profile of the speaker
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_LINK1)
  override abstract fun link1(): String?

  /**
   * The second link i.e. link to Google+ profile
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_LINK2)
  override abstract fun link2(): String ?

  /**
   * The third link i.e. to personal website / blog
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_LINK3)
  override abstract fun link3(): String?

  /**
   * The url to the profile picture of this speaker
   */
  @Nullable
  @ColumnName(SpeakerDaoSqlite.COL_PICTURE)
  override abstract fun profilePic(): String?

  abstract fun toContentValues(): ContentValues

  companion object {
    @JvmStatic
    fun mapper(): Func1<Cursor, SpeakerAutoValue> = AutoValue_SpeakerAutoValue.MAPPER

    fun create(id: String, name: String, info: String?, profilePicUrl: String?,
        company: String?, jobTitle: String?, link1: String?, link2: String?,
        link3: String?): SpeakerAutoValue = AutoValue_SpeakerAutoValue(id, name, info, company,
        jobTitle,
        link1, link2, link3, profilePicUrl)
  }
}