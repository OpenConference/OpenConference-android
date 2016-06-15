package com.openconference.search

import com.openconference.dagger.ApplicationComponent
import dagger.Component

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Component (
    modules = arrayOf(SearchViewModule::class), dependencies = arrayOf(ApplicationComponent::class))
@SearchScope
interface SearchComponent {

  fun searchPresenter(): SearchPresenter

  fun inject(a: SearchActivity)
}