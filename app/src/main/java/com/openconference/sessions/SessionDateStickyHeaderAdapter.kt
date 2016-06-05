package com.openconference.sessions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.eowise.recyclerview.stickyheaders.StickyHeadersAdapter
import com.openconference.R
import com.openconference.sessions.presentationmodel.SessionPresentationModel

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SessionDateStickyHeaderAdapter(private val inflater: LayoutInflater) : StickyHeadersAdapter<SessionDateStickyHeaderAdapter.DateViewHolder> {

  var sessions: List<SessionPresentationModel> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup?) =
      DateViewHolder(inflater.inflate(R.layout.item_session_date_sticky_header, parent, false))

  override fun onBindViewHolder(vh: DateViewHolder, position: Int) {
    val session = sessions[position]
    if (session.dateShort() != null) {
      vh.date.text = session.dateShort()
      vh.date.visibility = View.VISIBLE
    } else {
      vh.date.visibility = View.GONE
    }

    if (session.dayInWeek() != null){
      vh.dayInWeek.text = session.dayInWeek()
      vh.dayInWeek.visibility = View.VISIBLE
    } else {
      vh.dayInWeek.visibility = View.GONE
    }
  }

  override fun getHeaderId(position: Int): Long = sessions[position].getSectionId()

  class DateViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val dayInWeek: TextView by bindView(R.id.dayInWeek)
    val date: TextView by bindView(R.id.date)
  }
}