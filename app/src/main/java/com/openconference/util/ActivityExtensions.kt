package com.openconference.util

import android.app.Activity
import com.openconference.OpenConfApp


fun Activity.applicationComponent() = OpenConfApp.getApplicationComponent(application)