package com.openconference.search

import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.model.search.SearchEngine
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 * Presenter for [SearchView]
 *
 * @author Hannes Dorfmann
 */
class SearchPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val searchEngine: SearchEngine,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SearchView>(
    scheduler, errorMessageDeterminer) {

  fun search(query: String) {

    val trimmedQuery = query.trim()

    unsubscribe() // Unsubscribe previous search

    if (trimmedQuery.isBlank()) {
      view?.showSearchNotStarted()
      return
    }

    view?.showLoading()

    subscribe(searchEngine.search(trimmedQuery),
        { view?.showResults(it) },
        { view?.showError(errorMessageDeterminer.getErrorMessageRes(it)) }
    )
  }
}