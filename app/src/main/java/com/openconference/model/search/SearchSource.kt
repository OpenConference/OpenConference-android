package com.openconference.model.search

import com.openconference.model.search.SearchableItem
import rx.Observable

/**
 *
 *
 * @author Hannes Dorfmann
 */
interface SearchSource {

  fun search(query: String): Observable<List<SearchableItem>>
}