package org.openconf.util.lce

import android.view.View
import android.widget.TextView

/**
 * Default animation implementation
 *
 * @author Hannes Dorfmann
 */
interface LceAnimatable<M> {

  val contentView: View
  val errorView: TextView
  val loadingView: View

  fun isRestoringViewState(): Boolean
  fun getViewState() : LceViewState<M>

  /**
   * show the loading indicator
   */
  fun showLoading() {
    if (!isRestoringViewState() && loadingView.visibility != View.VISIBLE) {
      loadingView.alpha = 0f
      loadingView.animate().alpha(1f).withStartAction { View.VISIBLE }.start()
      contentView.animate().alpha(0f).withEndAction { contentView.visibility = View.GONE }.start()
    } else {
      loadingView.visibility = View.VISIBLE
      contentView.visibility = View.GONE
    }
    errorView.visibility = View.GONE

    getViewState().showLoading()
  }

  /**
   * Show the error indicator
   */
  fun showError(throwable: Throwable) {
    if (!isRestoringViewState() && errorView.visibility != View.VISIBLE) {
      errorView.alpha = 0f
      errorView.animate().alpha(1f).withStartAction { errorView.visibility = View.VISIBLE }.start()
      loadingView.animate().alpha(0f).withEndAction { loadingView.visibility = View.GONE }.start()
    } else {
      errorView.visibility = View.VISIBLE
      loadingView.visibility = View.GONE
    }

    contentView.visibility = View.GONE

    getViewState().showError(throwable)
  }

  /**
   * Show the content with the given data
   */
  fun showContent(data: M) {
    if (!isRestoringViewState() && contentView.visibility != View.VISIBLE) {
      contentView.alpha = 0f
      contentView.animate().alpha(
          1f).withStartAction { contentView.visibility = View.VISIBLE }.start()
      loadingView.animate().alpha(0f).withEndAction { loadingView.visibility = View.GONE }.start()
    } else {
      contentView.visibility = View.VISIBLE
      loadingView.visibility = View.GONE
    }

    errorView.visibility = View.GONE
    getViewState().showContent(data)
  }
}