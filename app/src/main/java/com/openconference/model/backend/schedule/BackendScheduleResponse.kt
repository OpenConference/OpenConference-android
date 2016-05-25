package com.openconference.model.backend.schedule

/**
 * This data structure will be retuned from
 *
 * @author Hannes Dorfmann
 */
data class BackendScheduleResponse<T> private constructor(val isNewerDataAvailable: Boolean, val data: List<T>) {

  companion object {
    fun <R> nothingChanged() = BackendScheduleResponse(false, emptyList<R>())

    fun <T> dataChanged(data: List<T>) = BackendScheduleResponse(true, data)
  }
}