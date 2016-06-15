package com.openconference.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AbsListItemAdapterDelegate
import com.openconference.R
import com.openconference.model.Speaker
import com.openconference.model.search.SearchableItem
import com.openconference.model.search.SpeakerSearchableItem
import com.openconference.util.picasso.CircleImageTransformation
import com.openconference.util.picasso.PicassoScrollListener
import com.squareup.picasso.Picasso

/**
 * Displays the description of a session
 *
 * @author Hannes Dorfmann
 */
open class SpeakerAdapterDelegate(protected val inflater: LayoutInflater, protected val picasso: Picasso, protected val clickListener: (Speaker) -> Unit) : AbsListItemAdapterDelegate<SpeakerSearchableItem, SearchableItem, SpeakerAdapterDelegate.SpeakerViewHolder>() {


  override fun isForViewType(item: SearchableItem, items: MutableList<SearchableItem>?,
      position: Int): Boolean = item is SpeakerSearchableItem

  override fun onCreateViewHolder(parent: ViewGroup): SpeakerViewHolder =
      SpeakerViewHolder(
          inflater.inflate(R.layout.item_search_speaker, parent, false), picasso,
          clickListener)

  override fun onBindViewHolder(item: SpeakerSearchableItem, viewHolder: SpeakerViewHolder) {
    viewHolder.bind(item.speaker)
  }

  class SpeakerViewHolder(v: View, val picasso: Picasso, val clickListener: (Speaker) -> Unit) : RecyclerView.ViewHolder(
      v) {

    init {
      v.setOnClickListener { clickListener(speaker) }
    }

    val name: TextView by bindView(R.id.name)
    val company: TextView by bindView(R.id.company)
    val profilePic: ImageView by bindView(R.id.profilePic)
    lateinit var speaker: Speaker

    inline fun bind(s: Speaker) {
      speaker = s
      name.text = s.name()
      val companyName = s.company()
      val jobTitle = s.jobTitle()

      picasso.load(s.profilePic())
          .placeholder(R.drawable.rounded_profile_pic_placeholder)
          .tag(PicassoScrollListener.TAG)
          .transform(CircleImageTransformation())
          .fit()
          .centerCrop()
          .into(profilePic)

      val companyJobTitle = StringBuilder()
      if (companyName != null) {
        companyJobTitle.append(companyName)
      }

      if (companyName != null && jobTitle != null) {
        companyJobTitle.append(", ")
      }

      if (jobTitle != null) {
        companyJobTitle.append(jobTitle)
      }

      if (companyJobTitle.length > 0) {
        company.text = companyJobTitle.toString()
        company.visibility = View.VISIBLE
      } else {
        company.visibility = View.GONE
      }
    }
  }
}