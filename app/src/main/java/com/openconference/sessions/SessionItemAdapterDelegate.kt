package com.openconference.sessions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.sessions.presentationmodel.GroupableSession
import com.openconference.sessions.presentationmodel.SessionPresentationModel

/**
 * Represents a Session item in a RecyclerView
 *
 * @author Hannes Dorfmann
 */
class SessionItemAdapterDelegate(protected val layoutInflater: LayoutInflater) : AbsListItemAdapterDelegate<SessionPresentationModel, GroupableSession, SessionItemAdapterDelegate.SessionItemViewHolder>() {


  override fun isForViewType(item: GroupableSession, items: MutableList<GroupableSession>?,
      position: Int): Boolean =
      item is SessionPresentationModel

  override fun onBindViewHolder(item: SessionPresentationModel, viewHolder: SessionItemViewHolder) {
    val session = item.session()
    viewHolder.title.text = session.title()

    // Time
    if (item.time() != null) {
      viewHolder.time.text = item.time()
      viewHolder.time.visibility = View.VISIBLE
    } else {
      viewHolder.time.visibility = View.GONE
    }

    // Location
    if (session.locationName() != null && session.locationName()!!.isNotEmpty()) {
      viewHolder.location.visibility = View.VISIBLE
      viewHolder.location.text = session.locationName()!!
    } else {
      viewHolder.location.visibility = View.GONE
    }

    // Speakers
    val speakers = item.speakers()
    if (speakers == null) {
      viewHolder.speakers.visibility = View.GONE
    } else {
      viewHolder.speakers.text = speakers.toString()
      viewHolder.speakers.visibility = View.VISIBLE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup): SessionItemViewHolder =
      SessionItemViewHolder(layoutInflater.inflate(R.layout.item_session, parent, false))

  /**
   * ViewHolder for a Session Item
   */
  class SessionItemViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val title: TextView by bindView(R.id.title)
    val speakers: TextView by bindView(R.id.speakers)
    val time: TextView by bindView(R.id.time)
    val location: TextView by bindView(R.id.location)
  }
}