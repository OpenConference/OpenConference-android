package com.openconference.sessiondetails

import com.openconference.dagger.ApplicationComponent
import dagger.Component

/**
 * SessionDetails component
 *
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(SessionDetailsModule::class),
    dependencies = arrayOf(ApplicationComponent::class))
@SessionDetailsScope
interface SessionDetailsComponent {

  fun sessionDetailsPresenter(): SessionDetailsPresenter
  fun inject(f: SessionDetailsFragment)
}