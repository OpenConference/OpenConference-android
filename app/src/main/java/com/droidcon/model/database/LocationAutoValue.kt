package com.droidcon.model.database

import android.content.ContentValues
import android.database.Cursor
import com.droidcon.model.Location
import com.droidcon.model.database.dao.LocationDaoSqlite
import com.gabrielittner.auto.value.cursor.ColumnName
import com.google.auto.value.AutoValue
import rx.functions.Func1

/**
 * A [Location] implementation with AutoValue
 *
 * @author Hannes Dorfmann
 */
@AutoValue
abstract class LocationAutoValue : Location {

  @ColumnName(LocationDaoSqlite.COL_ID)
  override abstract fun id(): String

  @ColumnName(LocationDaoSqlite.COL_NAME)
  override abstract fun name(): String

  abstract fun toContentValues(): ContentValues

  companion object {
    @JvmStatic
    fun mapper(): Func1<Cursor, LocationAutoValue> =
        AutoValue_LocationAutoValue.MAPPER

    fun create(id: String, name: String): LocationAutoValue = AutoValue_LocationAutoValue(id, name)
  }
}