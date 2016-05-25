package org.openconf.util

import android.app.Activity
import org.openconf.OpenConfApp


fun Activity.applicationComponent() = OpenConfApp.getApplicationComponent(application)