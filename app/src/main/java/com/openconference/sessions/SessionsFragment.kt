package com.openconference.sessions

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eowise.recyclerview.stickyheaders.StickyHeadersBuilder
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.Session
import com.openconference.sessions.presentationmodel.GroupableSession
import com.openconference.sessions.presentationmodel.SessionPresentationModel
import com.openconference.util.StableIdListAdapter
import com.openconference.util.applicationComponent
import com.openconference.util.findView
import com.openconference.util.layoutInflater
import com.openconference.util.lce.LceAnimatable
import com.openconference.util.lce.LceViewState
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
@FragmentWithArgs
open class SessionsFragment : SessionsView, LceAnimatable<List<SessionPresentationModel>>, MvpViewStateFragment<SessionsView, SessionsPresenter>() {


  override lateinit var content_view: View
  override lateinit var errorView: TextView
  override lateinit var loadingView: View
  override lateinit var emptyView: View
  protected lateinit var recyclerView: RecyclerView
  protected lateinit var adapter: ListDelegationAdapter<List<GroupableSession>>
  protected lateinit var stickyHeadersAdapter: SessionDateStickyHeaderAdapter
  protected lateinit var component: SessionsComponent

  @Inject protected lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FragmentArgs.inject(this)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    component = DaggerSessionsComponent.builder()
        .applicationComponent(applicationComponent())
        .sessionsModule(SessionsModule(activity))
        .build()
    component.inject(this)

    return inflater.inflate(R.layout.fragment_sessions, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    errorView = view.findView(R.id.errorView)
    loadingView = view.findView(R.id.loadingView)
    content_view = view.findView(R.id.contentView)
    recyclerView = view.findView(R.id.recyclerView)
    emptyView = view.findView(R.id.emptyView)

    errorView.setOnClickListener { loadData() }
    adapter = createAdapter()
    recyclerView.adapter = adapter
    stickyHeadersAdapter = SessionDateStickyHeaderAdapter(layoutInflater())
    recyclerView.layoutManager = createRecyclerViewLayoutManager()


    val stickyDecorator = StickyHeadersBuilder()
        .setAdapter(adapter)
        .setStickyHeadersAdapter(stickyHeadersAdapter, true)
        .setRecyclerView(recyclerView)
        .build()

    recyclerView.addItemDecoration(stickyDecorator)

  }

  /**
   * Creates an Adapter to display sessions in RecyclerView
   */
  open fun createAdapter(): ListDelegationAdapter<List<GroupableSession>> =
      StableIdListAdapter<List<GroupableSession>>(
          AdapterDelegatesManager<List<GroupableSession>>()
              .addDelegate(SessionItemAdapterDelegate(layoutInflater(),
                  { navigator.showSessionDetails(it) }))

          , { list, position -> list[position].getSessionId().hashCode().toLong() })

  /**
   * Creates the RecyclerViewLayoutManager
   */
  open fun createRecyclerViewLayoutManager() = LinearLayoutManager(activity)

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(@StringRes errorRes: Int) {
    super.showError(errorRes)
  }

  override fun showContent(data: List<SessionPresentationModel>) {
    adapter.items = data
    stickyHeadersAdapter.sessions = data
    adapter.notifyDataSetChanged()

    super.showContent(data)
  }

  override fun isDataEmpty(data: List<SessionPresentationModel>): Boolean = data.isEmpty()

  private inline fun loadData() = presenter.loadSessions()

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SessionsPresenter =
      component.sessionPresenter()

  override fun createViewState(): LceViewState<List<Session>> = LceViewState()

  override fun getViewState(): LceViewState<List<SessionPresentationModel>>? = super.getViewState() as LceViewState<List<SessionPresentationModel>>?
}