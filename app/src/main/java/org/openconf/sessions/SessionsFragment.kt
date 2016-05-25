package org.openconf.sessions

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import org.openconf.R
import org.openconf.model.Session
import com.droidcon.util.applicationComponent
import com.droidcon.util.lce.LceAnimatable
import com.droidcon.util.lce.LceViewState
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SessionsFragment : SessionsView, LceAnimatable<List<Session>>, MvpViewStateFragment<SessionsView, SessionsPresenter>() {

  override val contentView: View by bindView(R.id.contentView)
  override val errorView: TextView by bindView(R.id.errorView)
  override val loadingView: View by bindView(R.id.loadingView)
  private val recyclerView: RecyclerView by bindView(R.id.recyclerView)

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? =
      inflater.inflate(R.layout.fragment_sessions, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    errorView.setOnClickListener { loadData() }

    // TODO adapter
  }

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(throwable: Throwable) {
    super.showError(throwable)
  }

  override fun showContent(data: List<Session>) {
    super.showContent(data)
  }

  private fun loadData() = presenter.loadSessions()

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SessionsPresenter = applicationComponent().sessionPresenter()

  override fun createViewState(): LceViewState<List<Session>> = LceViewState()

  override fun getViewState(): LceViewState<List<Session>> = super.getViewState() as LceViewState<List<Session>>
}