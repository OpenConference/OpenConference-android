package com.openconference.search

import com.openconference.Navigator
import com.openconference.PhoneNavigator
import dagger.Module
import dagger.Provides

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class SearchViewModule(private val activity: SearchActivity){

  @Provides
  @SearchScope
  fun provideNavigator() : Navigator = PhoneNavigator(activity)

}