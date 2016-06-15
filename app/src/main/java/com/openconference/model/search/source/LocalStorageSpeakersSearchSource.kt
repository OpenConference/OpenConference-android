package com.openconference.model.search.source

import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.database.dao.SpeakerDao
import com.openconference.model.search.SearchSource
import com.openconference.model.search.SearchableItem
import com.openconference.model.search.SpeakerSearchableItem
import rx.Observable
import java.util.*

/**
 *
 *
 * @author Hannes Dorfmann
 */
class LocalStorageSpeakersSearchSource(private val awareObservableFactory: ScheduleDataAwareObservableFactory, private val speakerDao: SpeakerDao) : SearchSource {

  override fun search(
      query: String): Observable<List<SearchableItem>> =
      awareObservableFactory.create<List<SearchableItem>>(
          speakerDao.findSessionsWith(query)
              .map {
                val searchItems = ArrayList<SearchableItem>(it.size)
                it.forEach {
                  searchItems.add(SpeakerSearchableItem(it))
                }
                searchItems
              }
      )
}