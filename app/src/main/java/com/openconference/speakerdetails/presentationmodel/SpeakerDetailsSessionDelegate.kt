package com.openconference.speakerdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.model.Session
import com.openconference.speakerdetails.presentationmodel.SpeakerDetailsItem
import com.openconference.speakerdetails.presentationmodel.SpeakerSessionItem

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SpeakerDetailsSessionDelegate(val inflater: LayoutInflater, val clickListener: (Session) -> Unit) : AbsListItemAdapterDelegate<SpeakerSessionItem, SpeakerDetailsItem, SpeakerDetailsViewHolder>() {


  override fun isForViewType(item: SpeakerDetailsItem, items: MutableList<SpeakerDetailsItem>?,
      position: Int) = item is SpeakerSessionItem

  override fun onBindViewHolder(item: SpeakerSessionItem, viewHolder: SpeakerDetailsViewHolder) {
    viewHolder.bind(if (item.showIcon) R.drawable.ic_sessions_details else null, item.session.title()!!)
    viewHolder.itemView.isClickable = true
    viewHolder.itemView.setOnClickListener { clickListener(item.session) }
  }

  override fun onCreateViewHolder(parent: ViewGroup) = SpeakerDetailsViewHolder(
      inflater.inflate(R.layout.item_details_icon_text, parent, false))

}