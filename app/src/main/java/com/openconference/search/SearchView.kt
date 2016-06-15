package com.openconference.search

import android.support.annotation.StringRes
import com.hannesdorfmann.mosby.mvp.MvpView
import com.openconference.model.search.SearchableItem

/**
 *
 *
 * @author Hannes Dorfmann
 */
interface SearchView : MvpView {
  fun showLoading();
  fun showSearchNotStarted();
  fun showError(@StringRes errorMesgId: Int)
  fun showResults(result: List<SearchableItem>)
}