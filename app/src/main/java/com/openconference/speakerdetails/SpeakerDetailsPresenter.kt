package com.openconference.speakerdetails

import com.openconference.model.SessionsLoader
import com.openconference.model.SpeakersLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.sessiondetails.presentationmodel.SpeakerDetailsPresentationModelTransformer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import rx.Observable
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SpeakerDetailsPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val sessionsLoader: SessionsLoader,
    private val speakersLoader: SpeakersLoader,
    private val presentationModelTransformer: SpeakerDetailsPresentationModelTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SpeakerDetailsView>(
    scheduler, errorMessageDeterminer) {


  fun loadSpeakerDetails(speakerId: String) {
    view?.showLoading()
    subscribe(
        // TODO move this in a own class
        Observable.combineLatest(
            sessionsLoader.getSessionsOfSpeaker(speakerId),
            speakersLoader.getSpeaker(speakerId),
            { sessions, speaker -> presentationModelTransformer.transform(speaker, sessions) }),
        {
          view?.showContent(it)
        },
        {
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        })
  }
}