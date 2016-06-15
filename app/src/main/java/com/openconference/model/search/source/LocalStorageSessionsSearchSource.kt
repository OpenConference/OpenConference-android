package com.openconference.model.search.source

import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.database.dao.SessionDao
import com.openconference.model.search.SearchSource
import com.openconference.model.search.SearchableItem
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import rx.Observable

/**
 *
 *
 * @author Hannes Dorfmann
 */
class LocalStorageSessionsSearchSource(private val awareObservableFactory: ScheduleDataAwareObservableFactory, private val sessionDao: SessionDao, private val modelTransformer: SessionPresentationModelTransformer) : SearchSource {

  override fun search(
      query: String): Observable<List<SearchableItem>> = awareObservableFactory.create<List<SearchableItem>>(
      sessionDao.findSessionsWith(query)
          .map { modelTransformer.transform(it) }
  )
}