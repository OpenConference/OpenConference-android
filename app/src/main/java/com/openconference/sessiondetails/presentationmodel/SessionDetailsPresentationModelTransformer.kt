package com.openconference.sessiondetails.presentationmodel

import com.openconference.model.Session
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

/**
 * Responsible to transform a Session to [SessionDetailItem]
 *
 * @author Hannes Dorfmann
 */
interface SessionDetailsPresentationModelTransformer {

  fun transform(session: Session): SessionDetail
}

/**
 * Phone Session Details Presentation Model Transformer
 * @author Hannes Dorfmann
 */
// TODO Tablet
class PhoneSessionDetailsPresentationModelTransformer : SessionDetailsPresentationModelTransformer {

  private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

  override fun transform(session: Session): SessionDetail {

    val items = ArrayList<SessionDetailItem>()


    // TODO use settings for time zone (users preferences)
    val zoneId = ZoneId.systemDefault()

    val start = session.startTime()
    val localStart = if (start == null) null else LocalDateTime.ofInstant(start, zoneId)

    val end = session.endTime()
    val localEnd = if (end == null) null else LocalDateTime.ofInstant(end, zoneId)

    if (start != null) {
      val dateStr = if (end != null) {
        "${dateFormatter.format(localStart)} - ${dateFormatter.format(localEnd)}"
      } else {
        dateFormatter.format(localStart)
      }

      items.add(SessionDateTimeItem(dateStr))
    }

    // Location
    val locationName = session.locationName()
    if (locationName != null) items.add(SessionLocationItem(locationName))


    // Description
    val description = session.description()
    if (description != null) {
      if (items.isNotEmpty()) items.add(SessionSeparatorItem())
      items.add(SessionDescriptionItem(description))
    }

    // TODO TAGS
    // Tags
    /*
    val tags = session.tags()
    if (tags != null) items.add(SessionTagsItem(tags))
*/
    // Speakers
    if (session.speakers().isNotEmpty()) {
      items.add(SessionSeparatorItem())
    }
    session.speakers().forEach { items.add(SessionSpeakerItem(it)) }

    return SessionDetail(items, session.favorite())
  }
}