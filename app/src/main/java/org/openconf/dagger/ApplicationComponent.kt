package org.openconf.dagger

import com.droidcon.sessions.SessionsPresenter
import dagger.Component
import javax.inject.Singleton

/**
 * A Dagger component providing application wide dependencies
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(ApplicationModule::class))
@Singleton
interface ApplicationComponent {

  fun sessionPresenter(): SessionsPresenter
}