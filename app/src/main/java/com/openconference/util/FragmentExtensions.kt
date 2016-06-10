package com.openconference.util

import android.support.v4.app.Fragment
import com.openconference.OpenConfApp

fun Fragment.applicationComponent() = OpenConfApp.getApplicationComponent(activity)

fun Fragment.layoutInflater() = activity.layoutInflater

