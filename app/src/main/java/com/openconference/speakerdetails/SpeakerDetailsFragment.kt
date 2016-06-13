package com.openconference.sessiondetails

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.hannesdorfmann.adapterdelegates2.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates2.ListDelegationAdapter
import com.hannesdorfmann.fragmentargs.FragmentArgs
import com.hannesdorfmann.fragmentargs.annotation.Arg
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateFragment
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.Speaker
import com.openconference.speakerdetails.*
import com.openconference.speakerdetails.presentationmodel.SpeakerDetail
import com.openconference.speakerdetails.presentationmodel.SpeakerDetailsItem
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
open class SpeakerDetailsFragment : SpeakerDetailsView, LceAnimatable<SpeakerDetail>, MvpViewStateFragment<SpeakerDetailsView, SpeakerDetailsPresenter>() {

  @Arg lateinit var speaker: Speaker

  override lateinit var content_view: View
  override lateinit var errorView: TextView
  override lateinit var loadingView: View
  override lateinit var emptyView: View
  protected lateinit var recyclerView: RecyclerView
  protected lateinit var adapter: ListDelegationAdapter<List<SpeakerDetailsItem>>
  protected lateinit var component: SpeakerDetailsComponent
  protected lateinit var toolbar: Toolbar
  protected lateinit var collapsingToolbar: CollapsingToolbarLayout
  protected lateinit var profilePic: ImageView

  @Inject protected lateinit var navigator: Navigator
  @Inject protected lateinit var picasso: Picasso

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    FragmentArgs.inject(this)
    retainInstance = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {


    component = DaggerSpeakerDetailsComponent.builder()
        .applicationComponent(applicationComponent())
        .speakerDetailsModule(SpeakerDetailsModule(activity))
        .build()

    component.inject(this)

    return inflater.inflate(R.layout.fragment_speaker_details,
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
    profilePic = view.findView(R.id.image)
    profilePic.setColorFilter(
        ResourcesCompat.getColor(activity.resources, R.color.profilepic_darken, activity.theme),
        PorterDuff.Mode.DARKEN)


    toolbar.setNavigationOnClickListener { activity.finish() }


    adapter = createAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.addOnScrollListener(PicassoScrollListener(picasso))
  }

  fun createAdapter(): ListDelegationAdapter<List<SpeakerDetailsItem>> {
    val inflater = layoutInflater()

    return ListDelegationAdapter(
        AdapterDelegatesManager<List<SpeakerDetailsItem>>()
            .addDelegate(SpeakerDetailsJobInfoAdapterDelegate(inflater))
            .addDelegate(SpeakerDetailsLinkAdapterDelegate(inflater))
            .addDelegate(SpeakerDetailsBioAdapterDelegate(inflater))
            .addDelegate(
                SpeakerDetailsSessionDelegate(inflater,
                    { navigator.showSessionDetails(it) }))
    )
  }

  override fun getViewState(): LceViewState<SpeakerDetail>? = super.getViewState() as LceViewState<SpeakerDetail>?

  override fun showLoading() {
    super.showLoading()
  }

  override fun showError(@StringRes errorRes: Int) {
    super.showError(errorRes)
  }

  override fun showContent(data: SpeakerDetail) {
    adapter.items = data.detailsItems
    adapter.notifyDataSetChanged()

    toolbar.title = data.speakerName
    picasso.load(data.profilePic).placeholder(
        R.color.speakerslist_placeholder).fit().centerCrop().tag(PicassoScrollListener.TAG).into(
        profilePic)
    super.showContent(data)
  }

  override fun isDataEmpty(data: SpeakerDetail): Boolean = data.detailsItems.isEmpty()

  private inline fun loadData() = presenter.loadSpeakerDetails(speaker.id());

  override fun onNewViewStateInstance() = loadData()

  override fun createPresenter(): SpeakerDetailsPresenter = component.sessionDetailsPresenter()

  override fun createViewState(): LceViewState<SpeakerDetailsView> = LceViewState()

}