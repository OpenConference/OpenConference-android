package com.droidcon.util

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 *
 *
 * @author Hannes Dorfmann
 */
interface SchedulerTransformer {

  fun <T> schedule(observable: Observable<T>): Observable<T>
}

class DefaultSchedulerTransformer : SchedulerTransformer {

  override fun <T> schedule(observable: Observable<T>): Observable<T> =
      observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}