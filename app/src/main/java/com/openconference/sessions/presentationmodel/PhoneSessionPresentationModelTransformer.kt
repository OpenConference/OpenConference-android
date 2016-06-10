package com.openconference.sessions.presentationmodel

import com.openconference.model.Session
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.temporal.ChronoField
import java.util.*

/**
 * Transforms a [com.openconference.model.Session] to [SessionPresentationModel] for Phones
 * // TODO implement Tablet Transformer
 * // TODO UNIT TEST
 * @author Hannes Dorfmann
 */
class PhoneSessionPresentationModelTransformer(dateFormat: String) : SessionPresentationModelTransformer {

  private val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
  private val dayInWeekFormatter = DateTimeFormatter.ofPattern("EE")
  private val shortDateFormatter = DateTimeFormatter.ofPattern(dateFormat) // TODO implement format "MM/dd"

  override fun transform(sessions: List<Session>): List<SessionPresentationModel> {

    return sessions.mapTo(ArrayList(), { s ->

      // TODO use settings for time zone (users preferences)
      val zoneId = ZoneId.systemDefault()

      val start = s.startTime()
      val localStart = if (start == null) null else LocalDateTime.ofInstant(start, zoneId)

      val end = s.endTime()
      val localEnd = if (end == null) null else LocalDateTime.ofInstant(end, zoneId)

      val sectionId = if (localStart == null) {
        Long.MAX_VALUE
      } else {
        localStart.getLong(ChronoField.EPOCH_DAY)
      }

      val diw = if (localStart == null) null else dayInWeekFormatter.format(
          localStart).removeSuffix(".")
      val shortDate = if (localStart == null) null else shortDateFormatter.format(localStart)

      // time
      val time = if (localStart != null) {
        if (localEnd == null) {
          timeFormatter.format(localStart)
        } else {
          "${timeFormatter.format(localStart)} - ${timeFormatter.format(localEnd)}"
        }
      } else {
        null
      }

      // Speakers
      val speakers = s.speakers()
      val speakersStr = if (speakers.isEmpty()) {
        null
      } else {
        if (speakers.size == 1) {
          speakers[0].name()
        } else {
          val names = StringBuilder(speakers[0].name())
          for (i in 1..speakers.size - 1) {
            names.append(", ")
            names.append(speakers[i].name())
          }
          names.toString()
        }
      }

      SessionPresentationModel.create(sectionId, diw, shortDate, speakersStr, time, s)
    })
  }

}