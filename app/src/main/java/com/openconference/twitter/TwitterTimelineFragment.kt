package com.openconference.twitter

import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.R
import com.openconference.model.Speaker
import com.openconference.util.findView
import com.openconference.util.lce.LceAnimatable
import com.openconference.util.lce.LceViewState
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.Timeline
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter

/**
 * Displays a list of tweets
 *
 * @author Hannes Dorfmann
 */
@FragmentWithArgs
class TwitterTimelineFragment : TwitterTimelineView, LceAnimatable<Int>, MvpViewStateFragment<TwitterTimelineView, TwitterTimelinePresenter>() {


  override lateinit var content_view: View
  override lateinit var errorView: TextView
  override lateinit var loadingView: View
  override lateinit var emptyView: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_twitter_timeline,
      container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    content_view = view.findView(R.id.contentView)
    errorView = view.findView(R.id.errorView)
    loadingView = view.findView(R.id.loadingView)
    emptyView = view.findView(R.id.emptyView)

    errorView.setOnClickListener { loadData() }

    super.onViewCreated(view, savedInstanceState)
  }

  override fun setTimeline(timeline: Timeline<Tweet>) {
    (content_view as ListView).adapter = TweetTimelineListAdapter.Builder(activity).setTimeline(
        timeline).build()
  }

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(@StringRes errorRes: Int) {
    super.showError(errorRes)
  }

  override fun showContent(data: Int) {
    super.showContent(data)
  }

  override fun isDataEmpty(data: Int): Boolean = false // Not needed here

  private inline fun loadData() = presenter.loadNextTweets()

  override fun onNewViewStateInstance() = loadData()

  override
  fun createViewState(): LceViewState<List<Speaker>> = LceViewState()

  override fun getViewState(): LceViewState<Int>? = super.getViewState() as LceViewState<Int>?

  override fun createPresenter(): TwitterTimelinePresenter = TwitterTimelinePresenter()
}