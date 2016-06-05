package com.openconference.dagger

import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.SessionsLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.util.SchedulerTransformer
import dagger.Component
import javax.inject.Singleton

/**
 * A Dagger component providing application wide dependencies
 * @author Hannes Dorfmann
 */
@Component(modules = arrayOf(ApplicationModule::class))
@Singleton
interface ApplicationComponent {

  fun schedulerTransformer(): SchedulerTransformer

  fun scheduleDataAwareObservableFactory(): ScheduleDataAwareObservableFactory

  fun sessionLoader(): SessionsLoader

  fun errorMessageDeterminer(): ErrorMessageDeterminer

}