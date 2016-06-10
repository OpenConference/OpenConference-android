package com.openconference.myschedule.presentationmodel

import com.openconference.sessions.presentationmodel.SessionPresentationModel

/**
 * Presentation model for easier use in [com.openconference.sessions.MyScheduleView]
 *
 * @author Hannes Dorfmann
 */
data class MySchedulePresentationModel(val items: List<SessionPresentationModel>, var scrollToPosition: Int?)