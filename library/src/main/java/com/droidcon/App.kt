package com.droidcon

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

/**
 *
 *
 * @author Hannes Dorfmann
 */
class App : Application() {

  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}