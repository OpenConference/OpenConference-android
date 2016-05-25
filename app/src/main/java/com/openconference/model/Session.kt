package com.openconference.model

import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import org.threeten.bp.Instant

/**
 *
 * Contains the description of a Session
 * @author Hannes Dorfmann
 */
interface Session : Parcelable {

  /**
   * The session id
   */
  @NonNull
  fun id(): String

  /**
   * The title of the sessions
   */
  @Nullable
  fun title(): String?

  /**
   * The description of the speaker
   */
  @Nullable
  fun description(): String?

  /**
   * Optional tags for this session
   */
  @Nullable
  fun tags(): String?

  /**
   * The location id
   */
  @Nullable fun locationId(): String?

  /**
   * The location name
   */
  @Nullable fun locationName(): String?

  /**
   * Start date / time
   */
  @Nullable
  fun startTime(): Instant?

  /**
   * End date / time
   */
  @Nullable
  fun endTime(): Instant?

  @Nullable
  fun speakers(): List<Speaker>

  /**
   * Specifies whether or not this seesion has been marked by the user of the app as his favorite
   * which means it will be display in the users personal schedule.
   */
  @NonNull
  fun favorite(): Boolean

}