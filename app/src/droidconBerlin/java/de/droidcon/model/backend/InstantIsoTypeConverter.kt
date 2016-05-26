package de.droidcon.model.backend

import com.bluelinelabs.logansquare.typeconverters.StringBasedTypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.format.DateTimeFormatter

/**
 * Logan Square json parser type converter for Dates ins ISO format
 *
 * @author Hannes Dorfmann
 */
class InstantIsoTypeConverter : StringBasedTypeConverter<Instant>() {

  override fun convertToString(o: Instant?): String? = if (o == null) {
    null
  } else {
    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    timeFormatter.format(o)

  }

  override fun getFromString(str: String?): Instant? = if (str == null) {
    null
  } else {
    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    val accessor = timeFormatter.parse(str);
    Instant.from(accessor)
  }
}