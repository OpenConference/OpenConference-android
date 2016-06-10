package com.openconference.util

import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter

/**
 *
 *
 * @author Hannes Dorfmann
 */
class StableIdListAdapter<T : List<*>>(delegatesManager: AdapterDelegatesManager<T>, private val idDeterminer: (T, Int) -> Long) : ListDelegationAdapter<T>(
    delegatesManager) {

  init {
    setHasStableIds(true)
  }

  override fun getItemId(position: Int): Long {
    return idDeterminer(items, position)
  }
}