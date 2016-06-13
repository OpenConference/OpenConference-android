package com.openconference.model

/**
 * Indicates that a certain speaker has not been found.
 *
 * @author Hannes Dorfmann
 */
class SpeakerNotFoundException(msg: String) : Exception(msg)