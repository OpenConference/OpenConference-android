package com.droidcon.model.database

import android.os.Parcel
import com.ryanharter.auto.value.parcel.TypeAdapter
import org.threeten.bp.Instant

/**
 * A TypeAdapter for Auto-Value parcelable plugin
 *
 * @author Hannes Dorfmann
 */
class InstantParcelableTypeAdapter : TypeAdapter<Instant> {

  override fun toParcel(value: Instant, dest: Parcel) {
    dest.writeLong(value.toEpochMilli())
  }

  override fun fromParcel(parcel: Parcel): Instant {
    return Instant.ofEpochMilli(parcel.readLong())
  }
}