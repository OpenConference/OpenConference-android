package com.droidcon.sessions

import com.droidcon.model.SessionsLoader
import com.droidcon.util.RxPresenter
import com.droidcon.util.SchedulerTransformer
import javax.inject.Inject

/**
 * Presenter responsible to show display sessions in  [SessionsView]
 *
 * @author Hannes Dorfmann
 */
class SessionsPresenter @Inject constructor(scheduler: SchedulerTransformer, private val sessionsLoader: SessionsLoader) : RxPresenter<SessionsView>(
    scheduler) {

  fun loadSessions() {
    view?.showLoading()
    subscribe(sessionsLoader.allSessions(),
        {
          view?.showContent(it)
        },
        {
          view?.showError(it)
        }
    )
  }

}