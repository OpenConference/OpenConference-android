package com.droidcon.model.backend

import javax.inject.Inject

/**
 * Responsible to sync the local database with the corresponding backend
 * @author Hannes Dorfmann
 */
class BackendSync @Inject constructor(private val backend : Backend){

  /**
   * Loads the data from backend and stores it into the local persistent storage for offline support
   */
//  fun executeSync(): Observable<Unit> { }

  /**
   * Used to determine if the sync has been executed at least once (or in other words [#executeSync] has been run successfully at least once.
   */
//  fun isSynced(): Observable<Boolean> { }

}