package com.openconference.sessions

import com.openconference.dagger.ApplicationComponent
import dagger.Component

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Component (
    modules = arrayOf(SessionsModule::class), dependencies = arrayOf(ApplicationComponent::class))
@SessionsScope
interface SessionsComponent {

  fun sessionPresenter(): SessionsPresenter

  fun inject(f: SessionsFragment)
}