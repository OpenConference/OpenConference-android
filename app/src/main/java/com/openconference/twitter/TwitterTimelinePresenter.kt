package com.openconference.twitter

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import com.openconference.R
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.SearchTimeline
import com.twitter.sdk.android.tweetui.Timeline
import com.twitter.sdk.android.tweetui.TimelineResult
import timber.log.Timber

class TwitterTimelinePresenter : MvpBasePresenter<TwitterTimelineView>() {

  private val timeline: Timeline<Tweet> = SearchTimeline.Builder().query("#droidconDE").build()

  override fun attachView(view: TwitterTimelineView) {
    super.attachView(view)
    view.setTimeline(timeline)
  }

  fun loadNextTweets() {
    view?.showLoading()
    timeline.next(0, object : Callback<TimelineResult<Tweet>>() {

      override fun success(result: Result<TimelineResult<Tweet>>?) {
        view?.showContent(1) // Data will be bound automatically via view.setTimeline
      }

      override fun failure(exception: TwitterException?) {
        view?.showError(R.string.error_unknown)
        Timber.e(exception, "Error while loading Twitter")
      }
    })
  }
}