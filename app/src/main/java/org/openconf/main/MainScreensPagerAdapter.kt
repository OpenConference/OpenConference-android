package org.openconf.main

import android.graphics.Rect
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import com.droidcon.model.screen.Screen

/**
 * ViewPagerAdapter for MainScreens
 * @author Hannes Dorfmann
 */
class MainScreensPagerAdapter(private val activity: AppCompatActivity, private val screens: List<Screen>) : FragmentStatePagerAdapter(
    activity.supportFragmentManager) {


  override fun getItem(position: Int): Fragment = when (position) {
    else -> throw IllegalArgumentException("Unknown type for screen at position $position")
  }

  override fun getCount(): Int = screens.size

  override fun getPageTitle(position: Int): CharSequence? {

    // TODO color filer
    val image = ContextCompat.getDrawable(activity, screens[position].iconRes())
    image.bounds = Rect(0, 0, image.intrinsicWidth, image.intrinsicHeight)
    val sb = SpannableString(" ")  // TODO tablet layout
    val imageSpan = ImageSpan(image, ImageSpan.ALIGN_BOTTOM)
    sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return sb
  }

}