package com.droidcon.util

import android.support.v4.app.Fragment
import com.droidcon.OpenConfApp

fun Fragment.applicationComponent() = OpenConfApp.getApplicationComponent(activity)