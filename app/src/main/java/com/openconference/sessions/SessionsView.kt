package com.openconference.sessions

import com.openconference.model.Session
import com.openconference.util.lce.LceView

/**
 * Displays a list of sessions
 * @author Hannes Dorfmann
 */
interface SessionsView : LceView<List<Session>>