package org.openconf.model.screen

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

/**
 * A Screen represents a visual component (like a Fragment)
 * @author Hannes Dorfmann
 */
interface Screen {

  @StringRes
  fun titleRes(): Int

  @DrawableRes
  fun iconRes(): Int
}