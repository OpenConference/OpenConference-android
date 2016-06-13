package com.openconference.sessiondetails.presentationmodel

/**
 *
 * Presentation Model for [com.openconference.sessiondetails.SessionDetailsView]
 *
 * @author Hannes Dorfmann
 */
data class SessionDetail(val detailsItems: List<SessionDetailItem>, val inMySchedule: Boolean)