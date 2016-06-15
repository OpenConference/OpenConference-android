package com.openconference.sessions.presentationmodel

import com.google.auto.value.AutoValue
import com.openconference.model.Session
import com.openconference.model.search.SearchableItem

/**
 * Presentation Model of [com.openconference.model.Session] used by [com.openconference.sessions.SessionsView]
 * @author Hannes Dorfmann
 */
@AutoValue
abstract class SessionPresentationModel : GroupableSession, SearchableItem {


  abstract fun dayInWeek(): String?

  abstract fun dateShort(): String?

  abstract fun time(): String?

  abstract fun speakers(): String?

  abstract fun session(): Session

  companion object {
    fun create(id: Long, dayInWeek: String?, dateShort: String?, speakers: String?, time: String?,
        session: Session): SessionPresentationModel =
        AutoValue_SessionPresentationModel(id, session.id().toString(), dayInWeek, dateShort, time,
            speakers, session)
  }
}