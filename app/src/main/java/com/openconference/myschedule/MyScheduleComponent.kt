package com.openconference.sessions

import com.openconference.dagger.ApplicationComponent
import com.openconference.myschedule.MyScheduleScope
import dagger.Component

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Component (
    modules = arrayOf(MyScheduleModule::class), dependencies = arrayOf(ApplicationComponent::class))
@MyScheduleScope
interface MyScheduleComponent {

  fun mySchedulePresenter(): MySchedulePresenter
  fun inject(fragment: MyScheduleFragment)
}