package com.droidcon.util

import android.content.ContentValues
import org.threeten.bp.Instant


/**
 * Checks if the value is null and either inserts null into content value or the real value
 */
fun ContentValues.putOrNull(key: String, value: String?) =
    if (value == null) putNull(key) else put(key, value)


fun ContentValues.putOrNull(key: String, value: Instant?) =
    if (value == null) putNull(key) else put(key, value.toEpochMilli())