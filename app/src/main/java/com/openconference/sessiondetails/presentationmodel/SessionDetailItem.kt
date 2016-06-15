package com.openconference.sessiondetails.presentationmodel

import com.openconference.model.Speaker
import com.openconference.model.search.SearchableItem

/**
 * Represents the base class of elements that can be represented in this app
 *
 * @author Hannes Dorfmann
 */
interface SessionDetailItem

/**
 * Holds the data for displaying session details
 * @author Hannes Dorfmann
 */
data class SessionDescriptionItem(val description: String) : SessionDetailItem

/**
 * Tags with which this item has been tagged
 * @author Hannes Dorfmann
 */
data class SessionTagsItem(val tags: String) : SessionDetailItem

/**
 * Contains the location id
 * @author Hannes Dorfmann
 */
data class SessionLocationItem(val locationName: String) : SessionDetailItem

/**
 * Contains the start - end date of a sessoin
 */
data class SessionDateTimeItem(val dateTime: String) : SessionDetailItem

/**
 * Speaker of a session
 */
data class SessionSpeakerItem(val speaker: Speaker) : SessionDetailItem

/**
 * Represents a separator line
 */
class SessionSeparatorItem() : SessionDetailItem, SearchableItem