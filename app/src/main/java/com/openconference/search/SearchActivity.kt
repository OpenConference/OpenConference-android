package com.openconference.search

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import com.jakewharton.rxbinding.widget.RxSearchView
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.search.SearchableItem
import com.openconference.util.applicationComponent
import com.openconference.util.hideKeyboard
import com.openconference.util.showKeyboard
import com.primetime.search.SearchViewState
import com.squareup.picasso.Picasso
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SearchActivity : SearchView, MvpViewStateActivity<SearchView, SearchPresenter>() {

  private lateinit var autoTransition: Transition
  private lateinit var adapter: ListDelegationAdapter<List<SearchableItem>>
  private val searchView: android.widget.SearchView by bindView(R.id.searchView)
  private val container: ViewGroup by bindView(R.id.container)
  private val errorView: TextView by bindView(R.id.errorView)
  private val loadingView: View by bindView(R.id.loadingView)
  private val contentView: RecyclerView by bindView(R.id.contentView)
  private val noResult: View by bindView(R.id.noResult)
  private var lastQuery = ""

  private lateinit var component: SearchComponent

  @Inject lateinit var picasso: Picasso
  @Inject lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {


    component = DaggerSearchComponent.builder()
        .applicationComponent(applicationComponent())
        .searchViewModule(SearchViewModule(this))
        .build()

    component.inject(this)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)
    retainInstance = true
    autoTransition = TransitionInflater.from(this).inflateTransition(R.transition.auto)


    // init adapter
    val delegatesManager = AdapterDelegatesManager<List<SearchableItem>>()
    delegatesManager.addDelegate(SessionItemAdapterDelegate(layoutInflater, {
      searchView.hideKeyboard()
      navigator.showSessionDetails(it)
    }))
        .addDelegate(SpeakerAdapterDelegate(layoutInflater, picasso, {
          searchView.hideKeyboard()
          navigator.showSpeakerDetails(it)
        }))

    adapter = ListDelegationAdapter<List<SearchableItem>>(delegatesManager)
    contentView.adapter = adapter

    //  contentView.addOnScrollListener(PicassoScrollListener(picasso))
    contentView.layoutManager = LinearLayoutManager(this)

    RxSearchView.queryTextChangeEvents(searchView)
        .skip(2)
        .filter { it.queryText().toString() != lastQuery }
        .debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          if (it.isSubmitted) {
            searchView.hideKeyboard()
          }
          lastQuery = it.queryText().toString()
          presenter.search(lastQuery)
        }

    searchView.showKeyboard()
    findViewById(R.id.resultsWrapper)?.setOnClickListener { finish() }
  }

  override fun showResults(data: List<SearchableItem>) {
    castedViewState().setShowResults(data)
    if (!restoringViewState) {
      TransitionManager.beginDelayedTransition(container, autoTransition)
    }
    loadingView.visibility = View.GONE
    errorView.visibility = View.GONE

    adapter.items = data
    adapter.notifyDataSetChanged()

    if (data.isEmpty()) {
      contentView.visibility = View.GONE
      noResult.visibility = View.VISIBLE
    } else {
      contentView.visibility = View.VISIBLE
      noResult.visibility = View.GONE
    }
  }

  override fun createViewState() = SearchViewState()

  private inline fun castedViewState() = viewState as SearchViewState

  override fun createPresenter(): SearchPresenter = component.searchPresenter()

  override fun showLoading() {
    castedViewState().setShowLoading()
    if (!restoringViewState) {
      TransitionManager.beginDelayedTransition(container, autoTransition)
    }
    loadingView.visibility = View.VISIBLE
    errorView.visibility = View.GONE
    contentView.visibility = View.GONE
    noResult.visibility = View.GONE
  }

  override fun showSearchNotStarted() {
    castedViewState().setSearchNotStarted()
    if (!restoringViewState) {
      TransitionManager.beginDelayedTransition(container, autoTransition)
    }
    container.setOnClickListener { finish() }
    loadingView.visibility = View.GONE
    errorView.visibility = View.GONE
    contentView.visibility = View.GONE
    noResult.visibility = View.GONE
  }

  override fun showError(@StringRes errorMesgId: Int) {
    castedViewState().setShowError(errorMesgId)
    if (!restoringViewState) {
      TransitionManager.beginDelayedTransition(container, autoTransition)
    }
    loadingView.visibility = View.GONE
    errorView.visibility = View.VISIBLE
    errorView.setText(errorMesgId)
    contentView.visibility = View.GONE
    noResult.visibility = View.GONE
  }

  override fun onNewViewStateInstance() = showSearchNotStarted()

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    searchView.hideKeyboard()
  }

}