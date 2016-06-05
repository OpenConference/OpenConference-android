package com.openconference.sessions.presentationmodel

import com.openconference.model.Session

/**
 *Transforms a [com.openconference.model.Session] to [SessionPresentationModel]
 *
 * @author Hannes Dorfmann
 */
interface SessionPresentationModelTransformer {

  fun transform(s: List<Session>): List<SessionPresentationModel>
}