package com.openconference.sessiondetails

import com.openconference.model.SessionsLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.sessiondetails.presentationmodel.SessionDetailsPresentationModelTransformer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SessionDetailsPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val sessionsLoader: SessionsLoader,
    private val presentationModelTransformer: SessionDetailsPresentationModelTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SessionDetailsView>(
    scheduler, errorMessageDeterminer) {


  fun loadSession(sessionId: String) {
    subscribe(
        sessionsLoader.getSession(sessionId).map { presentationModelTransformer.transform(it) },
        {
          view?.showContent(it)
        },
        {
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        })
  }

}