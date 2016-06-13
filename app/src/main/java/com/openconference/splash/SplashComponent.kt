package com.openconference.splash

import com.openconference.dagger.ApplicationComponent
import dagger.Component

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Component(
    dependencies = arrayOf(ApplicationComponent::class))
@SplashScope
interface SplashComponent {

  fun splashPresenter(): SplashPresenter
}