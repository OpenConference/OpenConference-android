package com.openconference.sessiondetails

import com.openconference.sessiondetails.presentationmodel.SessionDetail
import com.openconference.util.lce.LceView

/**
 * Displays details about a certain Session
 *
 * @author Hannes Dorfmann
 */
interface SessionDetailsView : LceView<SessionDetail> {
  fun showSessionAddedToSchedule()
  fun showErrorWhileAddingSessionToSchedule()

  fun showSessionRemovedFromSchedule()
  fun showErrorWhileRemovingSessionFromSchedule()
}