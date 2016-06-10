package com.openconference.sessions

import com.openconference.model.SessionsLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.myschedule.presentationmodel.MySchedulePresentationModelTransformer
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 * Presenter responsible to show display sessions in  [SessionsView]
 *
 * @author Hannes Dorfmann
 */
class MySchedulePresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val sessionsLoader: SessionsLoader,
    private val sessionGroupTransformer: SessionPresentationModelTransformer,
    private val presentationModelTransformer: MySchedulePresentationModelTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<MyScheduleView>(
    scheduler, errorMessageDeterminer) {

  fun loadSessions() {
    view?.showLoading()
    subscribe(sessionsLoader.favoriteSessions()
        .map { sessionGroupTransformer.transform(it) }
        .map { presentationModelTransformer.transform(it) },
        {
          view?.showContent(it)
        },
        {
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        }
    )
  }

}