package com.droidcon.util.lce

import com.hannesdorfmann.mosby.mvp.viewstate.ViewState

/**
 * ViewState implementation for [LceView]
 *
 * @author Hannes Dorfmann
 */
class LceViewState<M> : ViewState<LceView<M>> {

  private enum class State {
    SHOW_LOADING, SHOW_ERROR, SHOW_CONTENT
  }

  private var data: M? = null
  private var error: Throwable? = null
  private var state = State.SHOW_LOADING

  fun showContent(data: M) {
    this.data = data
    this.state = State.SHOW_CONTENT
    this.error = null
  }

  fun showError(throwable: Throwable) {
    this.error = throwable
    this.state = State.SHOW_ERROR
    this.data = null
  }

  fun showLoading() {
    this.error = null
    this.data = null
    this.state = State.SHOW_LOADING
  }

  override fun apply(view: LceView<M>, retained: Boolean) =
      when (state) {
        State.SHOW_CONTENT -> view.showContent(data!!)
        State.SHOW_LOADING -> view.showLoading()
        State.SHOW_ERROR -> view.showError(error!!)
      }
}