package com.openconference.sessions

import com.openconference.model.SessionsLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 * Presenter responsible to show display sessions in  [SessionsView]
 *
 * @author Hannes Dorfmann
 */
class SessionsPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val sessionsLoader: SessionsLoader,
    private val presentationModelTransformer: SessionPresentationModelTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SessionsView>(
    scheduler, errorMessageDeterminer) {

  fun loadSessions() {
    view?.showLoading()
    subscribe(sessionsLoader.allSessions().map { presentationModelTransformer.transform(it) },
        {
          view?.showContent(it)
        },
        {
          it.printStackTrace()
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        }
    )
  }

}