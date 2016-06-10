package com.openconference.myschedule.presentationmodel

import com.openconference.sessions.presentationmodel.SessionPresentationModel

/**
 * Transforms a list of [SessionPresentationModel] to [MySchedulePresentationModel]
 *
 * @author Hannes Dorfmann
 */
class MySchedulePresentationModelTransformer {

  fun transform(sessions: List<SessionPresentationModel>): MySchedulePresentationModel {

    // TODO implement
    return MySchedulePresentationModel(sessions, null)
  }
}