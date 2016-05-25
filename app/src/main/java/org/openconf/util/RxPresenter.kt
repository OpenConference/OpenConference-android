package org.openconf.util

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import rx.Observable
import rx.subscriptions.CompositeSubscription

/**
 * A Presenter that automatically unsubscribes from Observable
 * once the view has been permanently detached
 * @author Hannes Dorfmann
 */
open class RxPresenter<V : MvpView>(private val schedulerTransformer: SchedulerTransformer) : MvpBasePresenter<V>() {

  protected val subscriptions = CompositeSubscription()

  /**
   * subscribes the given observable with the given lambdas.
   * Per default the presenters default [SchedulerTransformer] will be applied
   */
  protected fun <M> subscribe(observable: Observable<M>,
      onNext: (M) -> Unit,
      onError: (Throwable) -> Unit,
      onCompleted: (() -> Unit)? = null,
      scheduler: SchedulerTransformer = schedulerTransformer) {


    val o = scheduler.schedule(observable)

    if (onCompleted != null) {
      subscriptions.add(o.subscribe(onNext, onError, onCompleted))
    } else {
      subscriptions.add(o.subscribe(onNext, onError))
    }
  }

  override fun detachView(retainInstance: Boolean) {
    super.detachView(retainInstance)
    if (!retainInstance) {
      subscriptions.unsubscribe()
    }
  }
}