package com.openconference.sessions

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.Speaker
import com.openconference.speakers.DaggerSpeakersComponent
import com.openconference.speakers.SpeakerAdapterDelegate
import com.openconference.speakers.SpeakersComponent
import com.openconference.speakers.SpeakersView
import com.openconference.util.*
import com.openconference.util.lce.LceAnimatable
import com.openconference.util.lce.LceViewState
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
@FragmentWithArgs
open class SpeakersFragment : SpeakersView, LceAnimatable<List<Speaker>>, MvpViewStateFragment<SpeakersView, SpeakersPresenter>() {


  override lateinit var contentView: View
  override lateinit var errorView: TextView
  override lateinit var loadingView: View
  override lateinit var emptyView: View
  protected lateinit var recyclerView: RecyclerView
  protected lateinit var adapter: ListDelegationAdapter<List<Speaker>>

  protected lateinit var component: SpeakersComponent

  @Inject lateinit var navigator: Navigator
  @Inject lateinit var picasso: Picasso

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View {

    component = DaggerSpeakersComponent.builder()
        .applicationComponent(applicationComponent())
        .speakersModule(SpeakersModule(activity))
        .build()
    component.inject(this)

    return inflater.inflate(R.layout.fragment_my_schedule, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    errorView = view.findView(R.id.errorView)
    loadingView = view.findView(R.id.loadingView)
    contentView = view.findView(R.id.contentView)
    recyclerView = view.findView(R.id.recyclerView)
    emptyView = view.findView(R.id.emptyView)

    errorView.setOnClickListener { loadData() }
    adapter = createAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = createRecyclerViewLayoutManager()
  }

  /**
   * Creates an Adapter to display sessions in RecyclerView
   */
  open fun createAdapter(): ListDelegationAdapter<List<Speaker>> =
      StableIdListAdapter<List<Speaker>>(
          AdapterDelegatesManager<List<Speaker>>()
              .addDelegate(SpeakerAdapterDelegate(layoutInflater(), picasso,
                  { navigator.showSpeakerDetails(it) }))

          , { list, position -> list[position].id().hashCode().toLong() })

  /**
   * Creates the RecyclerViewLayoutManager
   */
  open fun createRecyclerViewLayoutManager(): RecyclerView.LayoutManager {

    val layout = GridLayoutManager(activity, getInt(R.integer.speakers_list_columns))

    layout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
      override fun getSpanSize(position: Int): Int = 1
    }

    return layout
  }

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(@StringRes errorRes: Int) {
    super.showError(errorRes)
  }

  override fun showContent(data: List<Speaker>) {
    adapter.items = data
    adapter.notifyDataSetChanged()

    super.showContent(data)
  }

  override fun isDataEmpty(data: List<Speaker>): Boolean = data.isEmpty()

  private inline fun loadData() = presenter.loadSpeakers()

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SpeakersPresenter =
      component.speakersPresenter()

  override
  fun createViewState(): LceViewState<List<Speaker>> = LceViewState()

  override fun getViewState(): LceViewState<List<Speaker>>? = super.getViewState() as LceViewState<List<Speaker>>?
}