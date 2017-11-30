package com.openconference.speakerdetails.presentationmodel

/**
 * Presentation Model containig all the data required to dispaly details about a Speaker
 *
 * @author Hannes Dorfmann
 */
data class SpeakerDetail(val speakerName: String, val profilePic: String?,
    val detailsItems: List<SpeakerDetailsItem>)

