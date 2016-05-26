package com.openconference

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.openconference.dagger.*

/**
 * Custom application mainly to integrate dagger
 *
 * @author Hannes Dorfmann
 */
open class OpenConfApp : Application() {

  private lateinit var applicationComponent: ApplicationComponent

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
    applicationComponent = buildApplicationComponent().build()
  }

  companion object {
    fun getApplicationComponent(context: Context): ApplicationComponent {
      val app = context.applicationContext as OpenConfApp
      return app.applicationComponent
    }
  }


  open fun buildApplicationComponent(): DaggerApplicationComponent.Builder {

    return DaggerApplicationComponent.builder()
        .daoModule(DaoModule(this))
        .loadersModule(LoadersModule())
        .schedulingModule(SchedulingModule())
        .backendModule(BackendModule(this))
        .applicationModule(ApplicationModule())
        .scheduleModule(ScheduleModule(this))
  }

}