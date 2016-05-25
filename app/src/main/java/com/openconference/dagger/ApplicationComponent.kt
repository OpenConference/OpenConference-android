package com.openconference.dagger

import com.openconference.sessions.SessionsPresenter
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