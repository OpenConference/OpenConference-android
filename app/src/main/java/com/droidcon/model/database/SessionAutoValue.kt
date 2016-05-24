package com.droidcon.model.database

import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import com.droidcon.model.Session
import com.droidcon.model.Speaker
import com.google.auto.value.AutoValue
import com.ryanharter.auto.value.parcel.ParcelAdapter
import org.threeten.bp.Instant

/**
 *
 * Contains the description of a Session
 * @author Hannes Dorfmann
 */
@AutoValue
abstract class SessionAutoValue : Session, Parcelable {

  /**
   * The session id
   */
  @NonNull
  override abstract fun id(): String

  /**
   * The title of the sessions
   */
  @Nullable
  override abstract fun title(): String?

  /**
   * The description of the speaker
   */
  @Nullable
  override abstract fun description(): String?

  /**
   * Optional tags for this session
   */
  @Nullable
  override abstract fun tags(): String?

  /**
   * Start date / time
   */
  @Nullable
  @ParcelAdapter(InstantParcelableTypeAdapter::class)
  override abstract fun startTime(): Instant?

  /**
   * End date / time
   */
  @Nullable
  @ParcelAdapter(InstantParcelableTypeAdapter::class)
  override abstract fun endTime(): Instant?

  /**
   * The location id
   */
  @Nullable
  override abstract fun locationId(): String?

  /**
   * The location name
   */
  @Nullable
  override abstract fun locationName(): String?

  /**
   * The speakers of this session
   */
  override abstract fun speakers(): List<Speaker>

  override abstract fun favorite(): Boolean

  companion object {

    fun create(id: String, title: String?, description: String?, tags: String?, start: Instant?,
        end: Instant?, locationId: String?, locationName: String?, favorite: Boolean,
        speakers: List<Speaker>): SessionAutoValue = AutoValue_SessionAutoValue(id, title,
        description, tags,
        start, end, locationId, locationName, speakers, favorite)
  }

}