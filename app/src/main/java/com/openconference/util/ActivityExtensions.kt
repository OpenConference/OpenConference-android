package com.openconference.util

import android.app.Activity
import android.support.annotation.IdRes
import android.util.DisplayMetrics
import android.view.View
import com.openconference.OpenConfApp


fun Activity.applicationComponent() = OpenConfApp.getApplicationComponent(application)

fun Activity.dpToPx(dp: Int): Float {
  val metrics = resources.displayMetrics;
  val px: Float = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT);
  return px;
}

fun <T : View> Activity.findView(@IdRes id: Int) = this.findViewById(id) as T