package com.openconference.model.database

import android.os.Parcel
import com.ryanharter.auto.value.parcel.TypeAdapter
import org.threeten.bp.Instant

/**
 * A TypeAdapter for Auto-Value parcelable plugin
 *
 * @author Hannes Dorfmann
 */
class InstantParcelableTypeAdapter : TypeAdapter<Instant> {

  private val NULL_VALUE = -1L;

  override fun toParcel(value: Instant?, dest: Parcel) {
    if (value == null)
      dest.writeLong(NULL_VALUE)
    else
      dest.writeLong(value.toEpochMilli())
  }

  override fun fromParcel(parcel: Parcel): Instant? {
    val ms = parcel.readLong()
    return if (ms == NULL_VALUE)
      null
    else
      Instant.ofEpochMilli(ms)
  }
}