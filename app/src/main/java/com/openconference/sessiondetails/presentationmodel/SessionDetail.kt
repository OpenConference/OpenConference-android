package com.openconference.sessiondetails.presentationmodel

import com.openconference.model.Session

/**
 *
 * Presentation Model for [com.openconference.sessiondetails.SessionDetailsView]
 *
 * @author Hannes Dorfmann
 */
data class SessionDetail(val sessionId: String, val sessionName: String?, val session: Session, val detailsItems: List<SessionDetailItem>, val inMySchedule: Boolean)