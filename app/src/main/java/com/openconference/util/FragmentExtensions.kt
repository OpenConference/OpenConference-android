package com.openconference.util

import android.app.Activity
import android.support.annotation.IntegerRes
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import com.openconference.OpenConfApp

fun Fragment.applicationComponent() = OpenConfApp.getApplicationComponent(activity)

fun Fragment.layoutInflater() = activity.layoutInflater

fun Fragment.getInt(@IntegerRes intRes: Int) = resources.getInteger(intRes)

fun Fragment.dpToPx(dp: Int): Float {
  val metrics = resources.displayMetrics;
  val px: Float = dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT);
  return px;
}