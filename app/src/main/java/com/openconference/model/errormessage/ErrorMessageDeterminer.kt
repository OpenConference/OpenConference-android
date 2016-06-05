package com.openconference.model.errormessage

import android.support.annotation.StringRes

/**
 * Responsible to determine the error message for a given error
 *
 * @author Hannes Dorfmann
 */
interface ErrorMessageDeterminer {

  @StringRes
  fun getErrorMessageRes(t : Throwable) : Int
}