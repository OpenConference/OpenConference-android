package com.openconference.speakerdetails.presentationmodel

import com.openconference.model.Session

/**
 * Common super interface for items that can be dispalyed in the speakers details
 *
 * @author Hannes Dorfmann
 */
interface SpeakerDetailsItem

/**
 * Information about the job of the speaker
 */
data class SpeakerJobInfoItem(val company: String?, val jobTitle: String?) : SpeakerDetailsItem

/**
 * A link like personal blog, twitter account etc. of the speaker
 */
data class SpeakerLinkItem(val url: String, val showIcon: Boolean) : SpeakerDetailsItem

/**
 * Containing the bio of the speaker
 */
data class SpeakerBioItem(val bio: String) : SpeakerDetailsItem

/**
 * Represents a session that will be given by the speaker
 */
data class SpeakerSessionItem(val session: Session, val showIcon: Boolean) : SpeakerDetailsItem