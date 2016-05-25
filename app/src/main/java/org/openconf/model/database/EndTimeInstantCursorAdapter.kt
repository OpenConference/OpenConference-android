package org.openconf.model.database

import android.database.Cursor
import com.droidcon.model.database.dao.SessionDaoSqlite
import org.threeten.bp.Instant

/**
 * A auto-value-cursor CursorAdapter
 *
 * @author Hannes Dorfmann
 */
class EndTimeInstantCursorAdapter {

  companion object {
    @JvmStatic
    fun createFromCursor(c: Cursor): Instant? {
      val index = c.getColumnIndex(SessionDaoSqlite.COL_END_TIME)
      return if (index >= 0) {
        if (c.isNull(index)) {
          null
        } else {
          Instant.ofEpochMilli(c.getLong(index))
        }
      } else null
    }
  }
}