package com.openconference.util

import android.support.annotation.IdRes
import android.view.View


fun <T : View> View.findView(@IdRes id: Int) = this.findViewById(id) as T