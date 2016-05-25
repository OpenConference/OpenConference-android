package org.openconf.model.database

import android.os.Parcel
import org.junit.Test
import org.mockito.Mockito
import org.threeten.bp.Instant
import kotlin.test.assertEquals

class InstantParcelableTypeAdapterTest {

  @Test
  fun writeToParcel() {
    val now = Instant.now()
    val nowMillis = now.toEpochMilli()

    val typeAdapter = InstantParcelableTypeAdapter()
    val parcel = Mockito.mock(Parcel::class.java)

    // Write to parcel
    typeAdapter.toParcel(now, parcel)
    Mockito.verify(parcel).writeLong(nowMillis)

    // Read from Parcel
    Mockito.`when`(parcel.readLong()).thenReturn(nowMillis)
    val instantFromParcel = typeAdapter.fromParcel(parcel)

    assertEquals(now, instantFromParcel)
  }
}