package com.openconference.dagger

import android.content.Context
import com.openconference.R
import com.openconference.model.ScheduleDataAwareObservableFactory
import com.openconference.model.database.dao.SessionDao
import com.openconference.model.database.dao.SpeakerDao
import com.openconference.model.search.DefaultSearchEngine
import com.openconference.model.search.SearchEngine
import com.openconference.model.search.SearchSource
import com.openconference.model.search.source.LocalStorageSessionsSearchSource
import com.openconference.model.search.source.LocalStorageSpeakersSearchSource
import com.openconference.sessions.presentationmodel.PhoneSessionPresentationModelTransformer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 *
 *
 * @author Hannes Dorfmann
 */
@Module
class SearchModule {


  data class SearchSources(val sources: List<SearchSource>)

  @Provides
  @Singleton
  fun provideSearchSources(
      @ApplicationContext context: Context,
      awareObservableFactory: ScheduleDataAwareObservableFactory,
      sessionDao: SessionDao, speakerDao: SpeakerDao) = SearchSources(
      listOf<SearchSource>(
          LocalStorageSessionsSearchSource(awareObservableFactory, sessionDao,
              PhoneSessionPresentationModelTransformer(context.getString(
                  R.string.sessions_sticky_date_format))),
          LocalStorageSpeakersSearchSource(awareObservableFactory, speakerDao)))

  @Provides
  @Singleton
  fun provideSearchEngine(searchSources: SearchSources): SearchEngine = DefaultSearchEngine(
      searchSources.sources)
}