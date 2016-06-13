package com.openconference.sessiondetails

import com.openconference.dagger.ApplicationComponent
import com.openconference.speakerdetails.SpeakerDetailsPresenter
import dagger.Component

/**
 * SessionDetails component
 *
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(SpeakerDetailsModule::class),
    dependencies = arrayOf(ApplicationComponent::class))
@SpeakerDetailsScope
interface SpeakerDetailsComponent {

  fun sessionDetailsPresenter(): SpeakerDetailsPresenter
  fun inject(f: SpeakerDetailsFragment)
}