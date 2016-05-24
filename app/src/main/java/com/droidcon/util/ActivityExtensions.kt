package com.droidcon.util

import android.app.Activity
import com.droidcon.OpenConfApp


fun Activity.applicationComponent() = OpenConfApp.getApplicationComponent(application)