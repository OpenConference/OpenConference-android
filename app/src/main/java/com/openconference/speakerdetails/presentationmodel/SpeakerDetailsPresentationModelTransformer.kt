package com.openconference.sessiondetails.presentationmodel

import com.openconference.model.Session
import com.openconference.model.Speaker
import com.openconference.speakerdetails.presentationmodel.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

/**
 * Responsible to transform a Session to [SpeakerDetail]
 *
 * @author Hannes Dorfmann
 */
interface SpeakerDetailsPresentationModelTransformer {

  fun transform(speaker: Speaker, sessions: List<Session>): SpeakerDetail
}

/**
 * Phone Speaker Details Presentation Model Transformer
 * @author Hannes Dorfmann
 */
// TODO Tablet
class PhoneSpeakerDetailsPresentationModelTransformer : SpeakerDetailsPresentationModelTransformer {

  private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)

  override fun transform(speaker: Speaker, sessions: List<Session>): SpeakerDetail {

    val items = ArrayList<SpeakerDetailsItem>()

    // Bio
    val bio = speaker.info()
    if (bio != null) {
      items.add(SpeakerBioItem(bio))
    }


    val company = speaker.company()
    val jobTitle = speaker.jobTitle()

    // Job Info
    if (company != null || jobTitle != null) {
      items.add(SpeakerJobInfoItem(company, jobTitle))
    }

    
    // Links
    var firstLink = true
    val link1 = speaker.link1()
    if (link1 != null && isValidLink(link1)) {
      items.add(SpeakerLinkItem(link1, firstLink))
      firstLink = false
    }

    val link2 = speaker.link2()
    if (link2 != null && isValidLink(link2)) {
      items.add(SpeakerLinkItem(link2, firstLink))
      firstLink = false
    }

    val link3 = speaker.link3()
    if (link3 != null && isValidLink(link3)) {
      items.add(SpeakerLinkItem(link3, firstLink))
      firstLink = false
    }

    // Sessions
    var firstSession = true
    sessions.filter { it.title() != null }.forEach {
      items.add(SpeakerSessionItem(it, firstSession))
      firstSession = false
    }

    return SpeakerDetail(speaker.name(), speaker.profilePic(), items)
  }

  private inline fun isValidLink(url: String): Boolean {
    // TODO implement
    return true
  }
}