package com.openconference.speakerdetails

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
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
class SpeakerDetailsSessionDelegate(val inflater: LayoutInflater, val clickListener: (Session) -> Unit) : AbsListItemAdapterDelegate<SpeakerSessionItem, SpeakerDetailsItem, SpeakerDetailsSessionDelegate.SessionViewHolder>() {


  override fun isForViewType(item: SpeakerDetailsItem, items: MutableList<SpeakerDetailsItem>?,
      position: Int) = item is SpeakerSessionItem

  override fun onBindViewHolder(item: SpeakerSessionItem,
      viewHolder: SessionViewHolder) {

    viewHolder.session = item.session
    viewHolder.bind(if (item.showIcon) R.drawable.ic_sessions_details else null,
        item.session.title()!!)
  }

  override fun onCreateViewHolder(parent: ViewGroup): SessionViewHolder =
      SessionViewHolder(inflater.inflate(R.layout.item_details_icon_text, parent, false),
          clickListener)

  class SessionViewHolder(v: View, val clickListener: (Session) -> Unit) : RecyclerView.ViewHolder(
      v) {

    lateinit var session: Session

    init {
      v.setOnClickListener { clickListener(session) }
    }

    val icon by bindView<ImageView>(R.id.icon)
    val text by bindView<TextView>(R.id.text)

    inline fun bind(@DrawableRes iconRes: Int?, t: String) {
      if (iconRes == null) {
        icon.visibility = View.INVISIBLE
      } else {
        icon.setImageDrawable(itemView.resources.getDrawable(iconRes))
      }

      text.text = t
    }
  }
}