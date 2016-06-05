package com.openconference.sessions

import com.openconference.sessions.presentationmodel.GroupableSession
import com.openconference.sessions.presentationmodel.SessionPresentationModel
import com.openconference.util.lce.LceView

/**
 * Displays a list of sessions
 * @author Hannes Dorfmann
 */
interface SessionsView : LceView<List<SessionPresentationModel>>