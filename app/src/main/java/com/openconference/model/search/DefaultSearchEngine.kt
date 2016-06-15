package com.openconference.model.search

import rx.Observable
import timber.log.Timber
import java.util.*

/**
 *
 *
 * @author Hannes Dorfmann
 */
class DefaultSearchEngine(private val searchSources: List<SearchSource>) : SearchEngine {

  override fun search(query: String): Observable<List<SearchableItem>> {

    val errorCatchingSources = ArrayList<Observable<List<SearchableItem>>>()
    var errorsCount = 0

    for (source in searchSources) {
      errorCatchingSources.add (
          source.search(query).onErrorReturn {
            errorsCount++
            Timber.e(it, "Error in SearchSource $source")
            if (errorsCount < errorCatchingSources.size) {
              emptyList()
            } else {
              throw it
            }
          }
      )
    }

    return Observable.combineLatest(errorCatchingSources, {
      val result = ArrayList<SearchableItem>()
      it.forEach {
        result.addAll(it as List<SearchableItem>)
      }
      result
    })
  }
}