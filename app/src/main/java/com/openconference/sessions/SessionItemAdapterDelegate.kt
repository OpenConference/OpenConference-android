package com.openconference.sessions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.model.Session

/**
 * Represents a Session item in a RecyclerView
 *
 * @author Hannes Dorfmann
 */
class SessionItemAdapterDelegate(protected val layoutInflater: LayoutInflater) : AbsListItemAdapterDelegate<Session, Session, SessionItemAdapterDelegate.SessionItemViewHolder>() {

  override fun isForViewType(item: Session, items: MutableList<Session>?, position: Int): Boolean =
      item is Session

  override fun onBindViewHolder(item: Session, viewHolder: SessionItemViewHolder) {
    viewHolder.title.text = item.title()

    if (item.locationName() != null && item.locationName()!!.isNotEmpty()) {
      viewHolder.location.visibility = View.VISIBLE
      viewHolder.location.text = item.locationName()!!
    } else {
      viewHolder.location.visibility = View.GONE
    }

    val speakers = item.speakers()

    if (speakers.isEmpty()) {
      viewHolder.speakers.visibility = View.GONE
    } else {
      if (speakers.size == 1) {
        viewHolder.speakers.text = speakers[0].name()
      } else {
        val names = StringBuilder(speakers[0].name())
        for (i in 1..speakers.size - 1) {
          names.append(", ")
          names.append(speakers[i].name())
        }
        viewHolder.speakers.text = names.toString()
      }
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
    val location: TextView by bindView(R.id.location)
  }
}