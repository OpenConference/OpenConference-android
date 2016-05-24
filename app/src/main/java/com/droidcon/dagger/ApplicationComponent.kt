package com.droidcon.dagger

import dagger.Component
import javax.inject.Singleton

/**
 * A Dagger component providing application wide dependencies
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(DaoModule::class, LoadersModule::class, SchedulingModule::class))
@Singleton
interface ApplicationComponent {


}