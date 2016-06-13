package de.droidcon

import com.crashlytics.android.Crashlytics
import timber.log.Timber

/**
 *
 *
 * @author Hannes Dorfmann
 */
class CrashlyticsTimberTree : Timber.Tree() {
  override fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {

    if (message != null) {
      Crashlytics.log(message)
    }

    if (t != null) {
      Crashlytics.logException(t)
    }

  }
}