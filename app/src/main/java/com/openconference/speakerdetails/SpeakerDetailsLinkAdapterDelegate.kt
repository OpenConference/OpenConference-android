package com.openconference.speakerdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.speakerdetails.presentationmodel.SpeakerDetailsItem
import com.openconference.speakerdetails.presentationmodel.SpeakerLinkItem

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SpeakerDetailsLinkAdapterDelegate(val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SpeakerLinkItem, SpeakerDetailsItem, SpeakerDetailsViewHolder>() {


  override fun isForViewType(item: SpeakerDetailsItem, items: MutableList<SpeakerDetailsItem>?,
      position: Int) = item is SpeakerLinkItem

  override fun onBindViewHolder(item: SpeakerLinkItem, viewHolder: SpeakerDetailsViewHolder) {
    viewHolder.bind(if (item.showIcon) {
      R.drawable.ic_link
    } else null, item.url)
  }

  override fun onCreateViewHolder(parent: ViewGroup) = SpeakerDetailsViewHolder(
      inflater.inflate(R.layout.item_details_icon_link_text, parent, false))

}