package com.openconference.speakers

import com.openconference.dagger.ApplicationComponent
import com.openconference.sessions.SpeakersFragment
import com.openconference.sessions.SpeakersModule
import com.openconference.sessions.SpeakersPresenter
import dagger.Component

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(SpeakersModule::class),
    dependencies = arrayOf(ApplicationComponent::class))
@SpeakersScope
interface SpeakersComponent {

  fun inject(f: SpeakersFragment)

  fun speakersPresenter(): SpeakersPresenter

}