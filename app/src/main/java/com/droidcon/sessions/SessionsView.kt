package com.droidcon.sessions

import com.droidcon.model.Session
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * Displays a list of sessions
 * @author Hannes Dorfmann
 */
interface SessionsView : MvpView {

  fun showLoading()
  fun setData(sessions: List<Session>)
  fun showContent()
  fun showError(throwable: Throwable)
}