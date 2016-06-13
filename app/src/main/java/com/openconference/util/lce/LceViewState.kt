package com.openconference.util.lce

import android.support.annotation.StringRes
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState

/**
 * ViewState implementation for [LceView]
 *
 * @author Hannes Dorfmann
 */
// TODO test
class LceViewState<M> : ViewState<LceView<M>> {

  private enum class State {
    SHOW_LOADING, SHOW_ERROR, SHOW_CONTENT
  }

  private var data: M? = null
  private var errorMsg: Int = -1
  private var state = State.SHOW_LOADING

  fun showContent(data: M) {
    this.data = data
    this.state = State.SHOW_CONTENT
    this.errorMsg = -1
  }

  fun showError(@StringRes errorMsgRes: Int) {
    this.errorMsg = errorMsgRes
    this.state = State.SHOW_ERROR
    this.data = null
  }

  fun showLoading() {
    this.errorMsg = -1
    this.data = null
    this.state = State.SHOW_LOADING
  }

  override fun apply(view: LceView<M>, retained: Boolean) =
      when (state) {
        State.SHOW_CONTENT -> view.showContent(data!!)
        State.SHOW_LOADING -> view.showLoading()
        State.SHOW_ERROR -> view.showError(errorMsg)
      }
}