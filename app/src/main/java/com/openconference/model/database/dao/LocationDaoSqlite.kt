package com.openconference.model.database.dao

import android.database.sqlite.SQLiteDatabase
import com.openconference.model.Location
import com.openconference.model.database.LocationAutoValue
import com.hannesdorfmann.sqlbrite.dao.Dao
import rx.Observable

/**
 * DAO implementation that uses SqlBrite
 *
 * @author Hannes Dorfmann
 */
open class LocationDaoSqlite : LocationDao, Dao() {

  companion object {
    const val TABLE = "Location"
    const val COL_ID = "id"
    const val COL_NAME = "name"
  }


  override fun createTable(database: SQLiteDatabase) {
    CREATE_TABLE(TABLE,
        "$COL_ID VARCHAR(20) PRIMARY KEY",
        "$COL_NAME TEXT NOT NULL")
        .execute(database)

  }

  override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

  }

  override fun getLocations(): Observable<List<Location>> =
      query(
          SELECT(COL_ID, COL_NAME).FROM(TABLE)
      ).run()
          .mapToList(LocationAutoValue.mapper())
          .map { it as List<Location> }

  override fun getById(id: String): Observable<Location> {
    throw UnsupportedOperationException()
  }

  override fun insertOrUpdate(id: String, name: String): Observable<Long>
      = insert(TABLE, LocationAutoValue.create(id, name).toContentValues(),
      SQLiteDatabase.CONFLICT_REPLACE)

  override fun remove(id: String): Observable<Int> = delete(TABLE, "$COL_ID = ?", id)

  override fun removeAll(): Observable<Int> = delete(TABLE)
}