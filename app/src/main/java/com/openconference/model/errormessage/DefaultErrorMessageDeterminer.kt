package com.openconference.model.errormessage

import com.openconference.R

/**
 * The default error message determiner
 *
 * @author Hannes Dorfmann
 */
class DefaultErrorMessageDeterminer : ErrorMessageDeterminer {

  override fun getErrorMessageRes(t: Throwable): Int = R.string.error_unknown
}