package org.openconf.model

import android.os.Parcelable
import org.openconf.model.database.dao.LocationDaoSqlite
import com.gabrielittner.auto.value.cursor.ColumnName

/**
 * Represents a location where a session takes place
 *
 * @author Hannes Dorfmann
 */
interface Location : Parcelable {

  /**
   * The id of the location
   */
  fun id(): String

  /**
   * The name of the location
   */
  fun name(): String
}