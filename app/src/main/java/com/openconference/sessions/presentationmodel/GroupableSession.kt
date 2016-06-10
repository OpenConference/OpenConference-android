package com.openconference.sessions.presentationmodel

import android.os.Parcelable

/**
 * Common interface to group session together
 *
 * @author Hannes Dorfmann
 */
interface GroupableSession : Parcelable {


  /**
   * Specify to which section this Session belongs. Typically sections are grouped by days
   */
  fun getSectionId(): Long

  /**
   * Specifies the id of the session
   */
  fun getSessionId(): String
}