package com.openconference.sessions

import com.openconference.model.SpeakersLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.speakers.SpeakersView
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 * Presenter responsible to show display sessions in  [SessionsView]
 *
 * @author Hannes Dorfmann
 */
class SpeakersPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val speakersLoader: SpeakersLoader,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SpeakersView>(
    scheduler, errorMessageDeterminer) {

  fun loadSpeakers() {
    view?.showLoading()
    subscribe(speakersLoader.allSpeakers(),
        {
          view?.showContent(it)
        },
        {
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        }
    )
  }

}