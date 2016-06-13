package com.openconference.sessiondetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.sessiondetails.presentationmodel.SessionDetailItem
import com.openconference.sessiondetails.presentationmodel.SessionLocationItem

/**
 * Displays the location of a session
 *
 * @author Hannes Dorfmann
 */
open class DetailsLocationAdapterDelegate(protected val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SessionLocationItem, SessionDetailItem, DetailsViewHolder>() {


  override fun isForViewType(item: SessionDetailItem, items: MutableList<SessionDetailItem>?,
      position: Int): Boolean = item is SessionLocationItem

  override fun onCreateViewHolder(parent: ViewGroup): DetailsViewHolder =
      DetailsViewHolder(
          inflater.inflate(R.layout.item_session_details_location, parent, false))

  override fun onBindViewHolder(item: SessionLocationItem, viewHolder: DetailsViewHolder) {
    viewHolder.text.text = item.locationName
  }

}
