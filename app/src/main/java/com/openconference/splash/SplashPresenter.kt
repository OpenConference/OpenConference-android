package com.openconference.splash

import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SplashPresenter @Inject constructor(schedulerTransformer: SchedulerTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer, private val observableFactory: ScheduleDataAwareObservableFactory) : RxPresenter<SplashView>(
    schedulerTransformer,
    errorMessageDeterminer) {


  fun start() {

    // Preload data in background
    subscribe(observableFactory.create(Observable.just(1)),
        {},
        {},
        {}
    ) // no callbacks needed

    // wait for two seconds
    val timer = Observable.timer(1500, TimeUnit.MILLISECONDS)
    subscribe(
        timer,
        { // onNext
        },
        {
          // onError
          view?.finishSplash()
        },
        {
          // onCompleted (timer just fire onCompleted)
          view?.finishSplash()
        }
    )
  }
}