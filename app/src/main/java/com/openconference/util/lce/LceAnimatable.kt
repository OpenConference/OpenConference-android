package com.openconference.util.lce

import android.support.annotation.StringRes
import android.view.View
import android.widget.TextView

/**
 * Default animation implementation
 *
 * @author Hannes Dorfmann
 */
interface LceAnimatable<M> {

  var content_view: View
  var errorView: TextView
  var loadingView: View
  var emptyView: View

  fun isRestoringViewState(): Boolean
  fun getViewState(): LceViewState<M>?

  /**
   * show the loading indicator
   */
  fun showLoading() {
    if (!isRestoringViewState() && loadingView.visibility != View.VISIBLE) {
      loadingView.alpha = 0f
      loadingView.animate().alpha(
          1f).withStartAction { loadingView.visibility = View.VISIBLE }.start()
      content_view.animate().alpha(0f).withEndAction { content_view.visibility = View.GONE }.start()
    } else {
      loadingView.visibility = View.VISIBLE
      content_view.visibility = View.GONE
    }
    errorView.visibility = View.GONE

    if (!isRestoringViewState()) {
      getViewState()!!.showLoading()
    }
  }

  /**
   * Show the error indicator
   */
  fun showError(@StringRes errorMsgRes: Int) {
    if (!isRestoringViewState() && errorView.visibility != View.VISIBLE) {
      errorView.alpha = 0f
      errorView.animate().alpha(1f).withStartAction { errorView.visibility = View.VISIBLE }.start()
      loadingView.animate().alpha(0f).withEndAction { loadingView.visibility = View.GONE }.start()
    } else {
      errorView.visibility = View.VISIBLE
      loadingView.visibility = View.GONE
    }

    errorView.setText(errorMsgRes)

    content_view.visibility = View.GONE

    if (!isRestoringViewState()) {
      getViewState()!!.showError(errorMsgRes)
    }
  }

  /**
   * Show the content with the given data
   */
  fun showContent(data: M) {

    emptyView.visibility = if (isDataEmpty(data)) {
      View.VISIBLE
    } else {
      View.GONE
    }

    if (!isRestoringViewState() && content_view.visibility != View.VISIBLE) {
      content_view.alpha = 0f
      content_view.animate().alpha(
          1f).withStartAction { content_view.visibility = View.VISIBLE }.start()
      loadingView.animate().alpha(0f).withEndAction { loadingView.visibility = View.GONE }.start()
    } else {
      content_view.visibility = View.VISIBLE
      loadingView.visibility = View.GONE
    }

    errorView.visibility = View.GONE
    if (!isRestoringViewState()) {
      getViewState()!!.showContent(data)
    }
  }

  /**
   * Used to determine if the data is empty so that the emty view should be displyed
   */
  fun isDataEmpty(data: M): Boolean
}