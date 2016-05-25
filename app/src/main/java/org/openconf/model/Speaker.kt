package org.openconf.model

import android.os.Parcelable
import android.support.annotation.NonNull
import android.support.annotation.Nullable

/**
 * Represents an Speaker
 * @author Hannes Dorfmann
 */
interface Speaker : Parcelable {

  @NonNull
  fun id(): String

  /**
   * The full name of the speaker
   */
  @NonNull
  fun name(): String

  /**
   * The bio / info about the speaker
   */
  @Nullable
  fun info(): String ?

  /**
   * The company's name the speaker is working for
   */
  @Nullable
  fun company(): String?

  /**
   * The job title / role in the company
   */
  @Nullable
  fun jobTitle(): String?

  /**
   * The first link i.e. link to twitter profile of the speaker
   */
  @Nullable
  fun link1(): String?

  /**
   * The second link i.e. link to Google+ profile
   */
  @Nullable
  fun link2(): String ?

  /**
   * The third link i.e. to personal website / blog
   */
  @Nullable
  fun link3(): String?

  /**
   * The url to the profile picture of this speaker
   */
  @Nullable
  fun profilePic(): String?
}