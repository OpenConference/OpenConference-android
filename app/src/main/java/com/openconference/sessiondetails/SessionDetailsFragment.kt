package com.openconference.sessiondetails

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.Session
import com.openconference.sessiondetails.presentationmodel.SessionDetail
import com.openconference.sessiondetails.presentationmodel.SessionDetailItem
import com.openconference.util.applicationComponent
import com.openconference.util.findView
import com.openconference.util.layoutInflater
import com.openconference.util.lce.LceAnimatable
import com.openconference.util.lce.LceViewState
import com.openconference.util.picasso.PicassoScrollListener
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Displays the details of a Session
 *
 * @author Hannes Dorfmann
 */
@FragmentWithArgs
open class SessionDetailsFragment : SessionDetailsView, LceAnimatable<SessionDetail>, MvpViewStateFragment<SessionDetailsView, SessionDetailsPresenter>() {

  @Arg lateinit var session: Session

  override lateinit var content_view: View
  override lateinit var errorView: TextView
  override lateinit var loadingView: View
  override lateinit var emptyView: View
  protected lateinit var recyclerView: RecyclerView
  protected lateinit var adapter: ListDelegationAdapter<List<SessionDetailItem>>
  protected lateinit var component: SessionDetailsComponent
  protected lateinit var toolbar: Toolbar
  protected lateinit var collapsingToolbar: CollapsingToolbarLayout
  protected lateinit var title: TextView
  protected lateinit var fab: FloatingActionButton
  lateinit var sessionDetails: SessionDetail
  private var runningFirstTime = true

  @Inject protected lateinit var navigator: Navigator
  @Inject protected lateinit var picasso: Picasso

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FragmentArgs.inject(this)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {

    component = DaggerSessionDetailsComponent.builder()
        .applicationComponent(applicationComponent())
        .sessionDetailsModule(SessionDetailsModule(activity))
        .build()

    component.inject(this)

    return inflater.inflate(R.layout.fragment_session_details,
        container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    content_view = view.findView(R.id.contentView)
    errorView = view.findView(R.id.errorView)
    emptyView = view.findView(R.id.emptyView)
    recyclerView = view.findView(R.id.recyclerView)
    loadingView = view.findView(R.id.loadingView)
    toolbar = view.findView(R.id.toolbar)
    collapsingToolbar = view.findView(R.id.collapsingToolbar)
    title = view.findView(R.id.title)
    fab = view.findView(R.id.fab)

    fab.setOnClickListener {
      val animTime = resources.getInteger(R.integer.add_to_schedule_duration)
      val animatedDrawable = fab.drawable as AnimatedVectorDrawable
      animatedDrawable.start()

      fab.isClickable = false

      // TODO is there really no better way to listen for callbacks?
      fab.postDelayed({
        if (sessionDetails.inMySchedule) {
          presenter.removeSessionFromSchedule(sessionDetails.sessionId)
        } else {
          presenter.addSessionToSchedule(sessionDetails.sessionId)
        }
      }, animTime + 100L)
    }

    toolbar.setNavigationOnClickListener { activity.finish() }
    // toolbar.title = session.title()
    title.text = session.title()

    adapter = createAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.addOnScrollListener(PicassoScrollListener(picasso))
  }

  fun createAdapter(): ListDelegationAdapter<List<SessionDetailItem>> {
    val inflater = layoutInflater()

    return ListDelegationAdapter(
        AdapterDelegatesManager<List<SessionDetailItem>>()
            .addDelegate(DetailsDateAdapterDelegate(inflater))
            .addDelegate(DetailsDescriptionAdapterDelegate(inflater))
            .addDelegate(DetailsLocationAdapterDelegate(inflater))
            .addDelegate(DetailsSeparatorAdapterDelegate(inflater))
            .addDelegate(DetailsSpeakerAdapterDelegate(inflater, picasso,
                { navigator.showSpeakerDetails(it) }))
    )
  }

  override fun getViewState(): LceViewState<SessionDetail>? = super.getViewState() as LceViewState<SessionDetail>?

  override fun showLoading() {
    super.showLoading()
    fab.visibility = View.GONE
  }

  override fun showError(@StringRes errorRes: Int) {
    super.showError(errorRes)
    fab.visibility = View.GONE
  }

  override fun showContent(data: SessionDetail) {
    this.sessionDetails = data
    adapter.items = data.detailsItems
    adapter.notifyDataSetChanged()

    setFabDrawable()
    fab.isClickable = true

    if (!isRestoringViewState && runningFirstTime) {
      runningFirstTime = false // Only animate on first appearance
      fab.scaleX = 0f
      fab.scaleY = 0f
      fab.visibility = View.VISIBLE
      fab.animate().scaleX(1f).scaleY(1f).setInterpolator(OvershootInterpolator()).setStartDelay(
          1000).start()
    } else {
      fab.visibility = View.VISIBLE
    }
    super.showContent(data)
  }

  override fun isDataEmpty(data: SessionDetail): Boolean = data.detailsItems.isEmpty()

  private inline fun loadData() = presenter.loadSession(session.id());

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SessionDetailsPresenter = component.sessionDetailsPresenter()

  override fun createViewState(): LceViewState<SessionDetailsView> = LceViewState()

  override fun showSessionAddedToSchedule() {
    Snackbar.make(content_view, R.string.session_details_added_to_schedule,
        Snackbar.LENGTH_LONG).show()
  }

  override fun showErrorWhileAddingSessionToSchedule() {
    Snackbar.make(content_view, R.string.error_session_details_added_to_schedule,
        Snackbar.LENGTH_LONG).show()
    fab.isClickable = true
    setFabDrawable()
  }

  override fun showSessionRemovedFromSchedule() {
    // Not needed
  }

  override fun showErrorWhileRemovingSessionFromSchedule() {
    Snackbar.make(content_view, R.string.error_session_details_removed_from_schedule,
        Snackbar.LENGTH_LONG).show()
    fab.isClickable = true
    setFabDrawable()
  }

  private inline fun setFabDrawable() {

    val drawable = fab.drawable as AnimatedVectorDrawable
    drawable.stop()

    if (sessionDetails.inMySchedule) {
      fab.setImageDrawable(
          resources.getDrawable(R.drawable.avd_remove_from_schedule,
              activity.theme).mutate().constantState.newDrawable())
    } else {
      fab.setImageDrawable(
          resources.getDrawable(R.drawable.avd_add_to_schedule,
              activity.theme).mutate().constantState.newDrawable())
    }
  }
}