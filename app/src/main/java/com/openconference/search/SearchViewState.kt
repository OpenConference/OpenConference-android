package com.primetime.search

import android.support.annotation.StringRes
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState
import com.openconference.model.search.SearchableItem
import com.openconference.search.SearchView

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SearchViewState : ViewState<SearchView> {

  private enum class State {
    NOT_STARTED, SHOW_LOADING, SHOW_ERROR, SHOW_RESULTS
  }

  private var state = State.NOT_STARTED
  private var error: Int = -1
  private var results: List<SearchableItem>? = null

  override fun apply(view: SearchView, retained: Boolean) = when (state) {
    State.NOT_STARTED -> view.showSearchNotStarted()
    State.SHOW_ERROR -> view.showError(error)
    State.SHOW_LOADING -> view.showLoading()
    State.SHOW_RESULTS -> view.showResults(results!!)
  }

  fun setShowError(@StringRes errorMesgId: Int) {
    error = errorMesgId
    results = null
    state = State.SHOW_ERROR

  }

  fun setSearchNotStarted() {
    state = State.NOT_STARTED

    this.error = -1
    this.results = null
  }

  fun setShowLoading() {
    state = State.SHOW_LOADING
    this.error = -1
    this.results = null
  }

  fun setShowResults(results: List<SearchableItem>) {
    state = State.SHOW_RESULTS
    this.results = results
    this.error = -1
  }
}