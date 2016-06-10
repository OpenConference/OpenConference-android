package com.openconference.util

import android.app.Activity
import android.util.DisplayMetrics
import com.openconference.OpenConfApp


fun Activity.applicationComponent() = OpenConfApp.getApplicationComponent(application)

fun Activity.dpToPx(dp: Int): Float {
  val metrics = resources.displayMetrics;
  val  px : Float = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT);
  return px;
}