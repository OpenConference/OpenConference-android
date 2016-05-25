package org.openconf.main

import org.openconf.dagger.ApplicationComponent
import dagger.Component

/**
 * Dagger Component for
 * @author Hannes Dorfmann
 */
@Component (
    modules = arrayOf(MainActivityModule::class), dependencies = arrayOf(ApplicationComponent::class))
@MainActivityScope
interface MainActivityComponent {
  fun inject(activity: ViewPagerMainActivity)
}