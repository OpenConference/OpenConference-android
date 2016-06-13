package com.openconference.speakerdetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.speakerdetails.presentationmodel.SpeakerDetailsItem
import com.openconference.speakerdetails.presentationmodel.SpeakerJobInfoItem

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SpeakerDetailsJobInfoAdapterDelegate(val inflater: LayoutInflater) : AbsListItemAdapterDelegate<SpeakerJobInfoItem, SpeakerDetailsItem, SpeakerDetailsJobInfoAdapterDelegate.JobInfoViewHolder>() {


  override fun isForViewType(item: SpeakerDetailsItem, items: MutableList<SpeakerDetailsItem>?,
      position: Int) = item is SpeakerJobInfoItem

  override fun onBindViewHolder(item: SpeakerJobInfoItem, viewHolder: JobInfoViewHolder) =
      viewHolder.bind(item)

  override fun onCreateViewHolder(parent: ViewGroup) = JobInfoViewHolder(
      inflater.inflate(R.layout.item_speaker_details_jobinfo, parent, false))

  class JobInfoViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    val jobTitle by bindView<TextView>(R.id.jobTitle)
    val companyName by bindView<TextView>(R.id.company)

    inline fun bind(info: SpeakerJobInfoItem) {
      val title = info.jobTitle
      if (title != null) {
        jobTitle.text = title
        jobTitle.visibility = View.VISIBLE
      } else {
        jobTitle.visibility = View.GONE
      }

      val company = info.company
      if (company != null) {
        companyName.text = company
        companyName.visibility = View.VISIBLE
      } else {
        companyName.visibility = View.GONE
      }
    }
  }

}