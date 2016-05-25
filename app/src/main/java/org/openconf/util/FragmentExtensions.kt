package org.openconf.util

import android.support.v4.app.Fragment
import org.openconf.OpenConfApp

fun Fragment.applicationComponent() = OpenConfApp.getApplicationComponent(activity)