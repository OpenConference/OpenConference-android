package com.openconference.util.lce

import android.support.annotation.StringRes
import com.hannesdorfmann.mosby.mvp.MvpView

/**
 * LCE (Loading-Content-Error) MVP View
 *
 * @author Hannes Dorfmann
 */
interface LceView<M> : MvpView {

  /**
   * show the loading indicator
   */
  fun showLoading()

  /**
   * Show the error indicator
   */
  fun showError(@StringRes errorMsgRes: Int)

  /**
   * Show the content with the given data
   */
  fun showContent(data: M)
}