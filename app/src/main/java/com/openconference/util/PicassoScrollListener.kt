package com.openconference.util

import android.support.v7.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * This is a scroll listener that listens for scroll events on recyclerviews
 * to avoid picasso loading images while scrolling (avoid laggy scroll behavior)
 */
class PicassoScrollListener(private val picasso: Picasso) : RecyclerView.OnScrollListener() {


  companion object {
    val TAG = "PicassoScrollTag"
  }

  private var previousScrollState = RecyclerView.SCROLL_STATE_IDLE
  private var scrollingFirstTime = true

  init {
    picasso.resumeTag(TAG)
  }

  override fun onScrollStateChanged(view: RecyclerView?, scrollState: Int) {
    if (scrollingFirstTime) {
      resume()
      scrollingFirstTime = false
    }

    // TO the picasso staff
    if (!isScrolling(scrollState) && isScrolling(previousScrollState)) {
      resume()
    }

    if (isScrolling(scrollState) && !isScrolling(previousScrollState)) {
      pause()
    }

    previousScrollState = scrollState

  }

  private inline fun isScrolling(scrollState: Int): Boolean {
    return scrollState == RecyclerView.SCROLL_STATE_DRAGGING || scrollState == RecyclerView.SCROLL_STATE_SETTLING
  }

  private inline fun resume() {
    picasso.resumeTag(TAG)
  }

  private inline fun pause() {
    picasso.pauseTag(TAG)
  }
}