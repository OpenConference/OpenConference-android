package com.openconference.sessiondetails.presentationmodel

/**
 *
 * Presentation Model for [com.openconference.sessiondetails.SessionDetailsView]
 *
 * @author Hannes Dorfmann
 */
data class SessionDetail(val sessionId: String, val sessionName: String?, val detailsItems: List<SessionDetailItem>, val inMySchedule: Boolean)