package com.openconference.dagger

import android.content.Context
import com.hannesdorfmann.sqlbrite.dao.DaoManager
import com.openconference.model.database.dao.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module providing DAO's
 *
 * @author Hannes Dorfmann
 */
@Module
@Singleton
class DaoModule(context: Context) {


  private val sessionDao: SessionDao
  private val speakerDao: SpeakerDao
  private val locationDao: LocationDao

  init {

    // DAO's
    sessionDao = SessionDaoSqlite()
    speakerDao = SpeakerDaoSqlite()
    locationDao = LocationDaoSqlite()
    DaoManager.with(context.applicationContext)
        .add(sessionDao)
        .add(speakerDao)
        .add(locationDao)
        .version(1)
        .databaseName("schedule.db")
        .build()
  }

  @Provides
  @Singleton
  fun provideSessionDao() = sessionDao

  @Provides
  @Singleton
  fun provideSpeakerDao() = speakerDao

  @Provides
  @Singleton
  fun provideLocationDao() = locationDao

}