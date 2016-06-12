package com.openconference.speakers

import com.openconference.model.Speaker
import com.openconference.util.lce.LceView

/**
 * View that displays a list of all speakers
 *
 * @author Hannes Dorfmann
 */
interface SpeakersView : LceView<List<Speaker>>