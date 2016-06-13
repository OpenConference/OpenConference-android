package com.openconference.sessiondetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.sessiondetails.presentationmodel.SessionDateTimeItem
import com.openconference.sessiondetails.presentationmodel.SessionDetailItem
import com.openconference.util.findView

/**
 * Displays the Date and time of a session
 *
 * @author Hannes Dorfmann
 */
open class DetailsDateAdapterDelegate(protected val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SessionDateTimeItem, SessionDetailItem, DetailsViewHolder>() {


  override fun isForViewType(item: SessionDetailItem, items: MutableList<SessionDetailItem>?,
      position: Int): Boolean = item is SessionDateTimeItem

  override fun onCreateViewHolder(parent: ViewGroup): DetailsViewHolder =
      DetailsViewHolder(
          inflater.inflate(R.layout.item_session_details_date, parent, false))

  override fun onBindViewHolder(item: SessionDateTimeItem, viewHolder: DetailsViewHolder) {
    viewHolder.text.text = item.dateTime
  }

}

class DetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
  val text: TextView = v.findView(R.id.text)
}