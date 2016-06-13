package com.openconference.speakerdetails

import android.support.annotation.DrawableRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import com.openconference.R

/**
 * ViewHolder for icon text combination
 *
 * @author Hannes Dorfmann
 */
open class SpeakerDetailsViewHolder(v: View) : RecyclerView.ViewHolder(v) {

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
