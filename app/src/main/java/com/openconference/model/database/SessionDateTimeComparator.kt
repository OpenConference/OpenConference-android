package com.openconference.model.database

import com.openconference.model.Session
import java.util.*

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SessionDateTimeComparator : Comparator<Session> {

  override fun compare(lhs: Session, rhs: Session): Int {

    if (lhs.startTime() == null && rhs.startTime() == null) {
      return 0
    }

    if (lhs.startTime() == null && rhs.startTime() != null){
      return 1
    }

    if (lhs.startTime() != null && rhs.startTime() == null){
      return -1
    }


    val dateComparisonResult =  lhs.startTime()!!.compareTo(rhs.startTime())
    if (dateComparisonResult != 0){
      return dateComparisonResult
    }

    val lhsLocationName = lhs.locationName()
    val rhsLocationName = rhs.locationName()

    if (lhsLocationName == null && rhsLocationName == null){
      return 0
    }


    if (lhsLocationName == null && rhsLocationName != null){
      return 1
    }

    if (lhsLocationName != null && rhsLocationName == null){
      return -1
    }

    return lhsLocationName!!.compareTo(rhsLocationName!!)
  }
}