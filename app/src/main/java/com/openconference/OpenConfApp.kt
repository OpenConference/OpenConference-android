package com.openconference

import android.app.Application
import android.content.Context
import com.openconference.dagger.*
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 * Custom application mainly to integrate dagger
 * 
 * @author Hannes Dorfmann
 */
class OpenConfApp : Application() {

  private var applicationComponent: ApplicationComponent? = null

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }

  companion object {
    fun getApplicationComponent(context: Context): ApplicationComponent {
      val app = context.applicationContext as OpenConfApp
      if (app.applicationComponent == null) {
        app.applicationComponent =
            DaggerApplicationComponent.builder()
                .daoModule(DaoModule(context))
                .loadersModule(LoadersModule())
                .schedulingModule(SchedulingModule())
                .build()
      }

      return app.applicationComponent!!
    }
  }
}