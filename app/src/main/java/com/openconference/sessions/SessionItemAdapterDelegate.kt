package com.openconference.sessions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.model.Session
import com.openconference.sessions.presentationmodel.GroupableSession
import com.openconference.sessions.presentationmodel.SessionPresentationModel

/**
 * Represents a Session item in a RecyclerView
 *
 * @author Hannes Dorfmann
 */
class SessionItemAdapterDelegate(protected val layoutInflater: LayoutInflater, private val clickListener: (Session) -> Unit) : AbsListItemAdapterDelegate<SessionPresentationModel, GroupableSession, SessionItemAdapterDelegate.SessionItemViewHolder>() {


  override fun isForViewType(item: GroupableSession, items: MutableList<GroupableSession>?,
      position: Int): Boolean =
      item is SessionPresentationModel

  override fun onBindViewHolder(item: SessionPresentationModel, viewHolder: SessionItemViewHolder) {
    val session = item.session()
    viewHolder.session = session
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

    // Favorite
    if (session.favorite()) {
      viewHolder.favorite.visibility = View.VISIBLE
    } else {
      viewHolder.favorite.visibility = View.GONE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup): SessionItemViewHolder =
      SessionItemViewHolder(layoutInflater.inflate(R.layout.item_session, parent, false),
          clickListener)

  /**
   * ViewHolder for a Session Item
   */
  class SessionItemViewHolder(v: View, clickListener: (Session) -> Unit) : RecyclerView.ViewHolder(
      v) {

    init {
      v.setOnClickListener({ clickListener(session!!) })
    }

    val title: TextView by bindView(R.id.title)
    val speakers: TextView by bindView(R.id.speakers)
    val time: TextView by bindView(R.id.time)
    val location: TextView by bindView(R.id.location)
    val favorite: ImageView by bindView(R.id.favorite)
    var session: Session? = null
  }
}
