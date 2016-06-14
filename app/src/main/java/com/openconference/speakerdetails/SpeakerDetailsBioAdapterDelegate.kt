package com.openconference.speakerdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.speakerdetails.presentationmodel.SpeakerBioItem
import com.openconference.speakerdetails.presentationmodel.SpeakerDetailsItem

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SpeakerDetailsBioAdapterDelegate(val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SpeakerBioItem, SpeakerDetailsItem, SpeakerDetailsViewHolder>() {


  override fun isForViewType(item: SpeakerDetailsItem, items: MutableList<SpeakerDetailsItem>?,
      position: Int) = item is SpeakerBioItem

  override fun onBindViewHolder(item: SpeakerBioItem, viewHolder: SpeakerDetailsViewHolder) =
      viewHolder.bind(null, item.bio)

  override fun onCreateViewHolder(parent: ViewGroup): SpeakerDetailsViewHolder {
    val view = inflater.inflate(R.layout.item_details_icon_text, parent, false)
    view.isClickable = false
    return SpeakerDetailsViewHolder(view)
  }

}