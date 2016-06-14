package com.openconference.twitter

import com.openconference.util.lce.LceView
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.Timeline

/**
 * Displays a list of item in the timeline
 *
 * @author Hannes Dorfmann
 */
interface TwitterTimelineView : LceView<Int> {
  fun setTimeline(timeline: Timeline<Tweet>)
}