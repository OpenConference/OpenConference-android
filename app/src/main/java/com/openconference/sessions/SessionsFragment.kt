package com.openconference.sessions

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.R
import com.openconference.model.Session
import com.openconference.util.applicationComponent
import com.openconference.util.layoutInflater
import com.openconference.util.lce.LceAnimatable
import com.openconference.util.lce.LceViewState

/**
 *
 *
 * @author Hannes Dorfmann
 */
@FragmentWithArgs
open class SessionsFragment : SessionsView, LceAnimatable<List<Session>>, MvpViewStateFragment<SessionsView, SessionsPresenter>() {


  override val contentView: View by bindView(R.id.contentView)
  override val errorView: TextView by bindView(R.id.errorView)
  override val loadingView: View by bindView(R.id.loadingView)
  protected val recyclerView: RecyclerView by bindView(R.id.recyclerView)
  protected lateinit var adapter: ListDelegationAdapter<List<Session>>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.fragment_sessions, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    errorView.setOnClickListener { loadData() }
    adapter = createAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = createRecyclerViewLayoutManager()

  }

  /**
   * Creates an Adapter to display sessions in RecyclerView
   */
  open fun createAdapter() = ListDelegationAdapter<List<Session>>(
      AdapterDelegatesManager<List<Session>>()
          .addDelegate(SessionItemAdapterDelegate(layoutInflater()))
  )

  /**
   * Creates the RecyclerViewLayoutManager
   */
  open fun createRecyclerViewLayoutManager() = LinearLayoutManager(activity)

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(throwable: Throwable) {
    super.showError(throwable)
  }

  override fun showContent(data: List<Session>) {
    adapter.items = data
    adapter.notifyDataSetChanged()
    super.showContent(data)
  }

  private inline fun loadData() = presenter.loadSessions()

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SessionsPresenter = applicationComponent().sessionPresenter()

  override fun createViewState(): LceViewState<List<Session>> = LceViewState()

  override fun getViewState(): LceViewState<List<Session>>? = super.getViewState() as LceViewState<List<Session>>?
}