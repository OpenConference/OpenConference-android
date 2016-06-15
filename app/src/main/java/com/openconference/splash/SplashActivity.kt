package com.openconference.splash

import android.content.Intent
import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState
import com.openconference.R
import com.openconference.main.ViewPagerMainActivity
import com.openconference.util.applicationComponent

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SplashActivity : SplashView, MvpViewStateActivity<SplashView, SplashPresenter>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    retainInstance = true

  }

  override fun finishSplash() {
    finish()
    startActivity(Intent(this, ViewPagerMainActivity::class.java))
    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
  }

  override fun createPresenter(): SplashPresenter =
      DaggerSplashComponent.builder().applicationComponent(
          applicationComponent()).build().splashPresenter()

  override fun createViewState(): ViewState<SplashView> = ViewState<com.openconference.splash.SplashView> { view, retained -> } // dummy, does nothing, not needed

  override fun onNewViewStateInstance() = presenter.start()
}