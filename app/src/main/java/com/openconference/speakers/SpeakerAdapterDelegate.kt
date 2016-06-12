package com.openconference.speakers

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
import com.openconference.util.PicassoScrollListener
import com.squareup.picasso.Picasso

/**
 * Displays an profile pic image and name of a given Speaker
 *
 * @author Hannes Dorfmann
 */
class SpeakerAdapterDelegate(
    private val inflater: LayoutInflater,
    private val picasso: Picasso,
    private val clickListener: (Speaker) -> Unit) : AbsListItemAdapterDelegate<Speaker, Speaker, SpeakerAdapterDelegate.SpeakerViewHolder>() {

  override fun isForViewType(item: Speaker, items: MutableList<Speaker>?, position: Int): Boolean =
      item is Speaker

  override fun onBindViewHolder(item: Speaker, viewHolder: SpeakerViewHolder) =
      viewHolder.bind(item)

  override fun onCreateViewHolder(parent: ViewGroup): SpeakerViewHolder =
      SpeakerViewHolder(inflater.inflate(R.layout.item_speaker, parent, false), picasso,
          clickListener)

  class SpeakerViewHolder(v: View, val picasso: Picasso, clickListener: (Speaker) -> Unit) : RecyclerView.ViewHolder(
      v) {

    init {
      v.setOnClickListener { clickListener(speaker) }
    }

    val image: ImageView by bindView(R.id.image)
    val name: TextView by bindView(R.id.name)
    lateinit var speaker: Speaker

    inline fun bind(s: Speaker) {
      speaker = s
      name.text = s.name()
      picasso.load(speaker.profilePic())
          .tag(PicassoScrollListener.TAG)
          .centerCrop()
          .fit()
          .placeholder(R.color.speakerslist_placeholder)
          .into(image)
    }
  }
}