package com.openconference.util

import android.content.Context
import android.support.annotation.IdRes
import android.view.View
import android.view.inputmethod.InputMethodManager


fun <T : View> View.findView(@IdRes id: Int) = this.findViewById(id) as T

fun View.hideKeyboard() {
  (context.getSystemService(
      Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
      windowToken, 0)
}

fun View.showKeyboard() {
  (context.getSystemService(
      Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
         InputMethodManager.SHOW_FORCED, 0)
}


