package com.openconference.sessiondetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.sessiondetails.presentationmodel.SessionDescriptionItem
import com.openconference.sessiondetails.presentationmodel.SessionDetailItem
import com.openconference.util.findView

/**
 * Displays the description of a session
 *
 * @author Hannes Dorfmann
 */
open class DetailsDescriptionAdapterDelegate(protected val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SessionDescriptionItem, SessionDetailItem, DetailsDescriptionAdapterDelegate.DescriptionViewHolder>() {


  override fun isForViewType(item: SessionDetailItem, items: MutableList<SessionDetailItem>?,
      position: Int): Boolean = item is SessionDescriptionItem

  override fun onCreateViewHolder(parent: ViewGroup): DescriptionViewHolder =
      DescriptionViewHolder(
          inflater.inflate(R.layout.item_session_details_description, parent, false))

  override fun onBindViewHolder(item: SessionDescriptionItem, viewHolder: DescriptionViewHolder) {
    viewHolder.description.text = item.description
  }

  class DescriptionViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val description: TextView = v.findView(R.id.description)
  }
}