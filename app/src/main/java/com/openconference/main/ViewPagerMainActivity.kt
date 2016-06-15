package com.openconference.main

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import butterknife.bindView
import com.openconference.Navigator
import com.openconference.R
import com.openconference.model.screen.Screen
import com.openconference.model.screen.Screens
import com.openconference.util.applicationComponent
import de.psdev.licensesdialog.LicensesDialog
import javax.inject.Inject

class ViewPagerMainActivity : AppCompatActivity() {

  companion object {
    val KEY_TAB_INDEX = "ViewPagerMainActivity.TAB_INDEX"
  }

  @Inject lateinit var screens: Screens
  @Inject lateinit var navigator: Navigator
  private val viewPager by bindView<ViewPager>(R.id.viewpager)
  private val tabs by bindView<TabLayout>(R.id.tabs)
  private val toolbar by bindView<Toolbar>(R.id.toolbar)
  private var currentPageIndex = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    DaggerMainActivityComponent.builder()
        .applicationComponent(applicationComponent())
        .mainActivityModule(MainActivityModule(this))
        .build()
        .inject(this)


    // Setup UI
    viewPager.setPageMarginDrawable(R.drawable.viewpager_separator)
    viewPager.pageMargin = resources.getDimensionPixelOffset(R.dimen.viewpager_page_margin)
    viewPager.adapter = MainScreensPagerAdapter(this, screens.screens)
    tabs.setupWithViewPager(viewPager)

    setSupportActionBar(toolbar)

    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {

      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

      }

      override fun onPageSelected(position: Int) {
        setToolbarTitle(position)
        currentPageIndex = position
      }
    })

    if (savedInstanceState != null) {
      currentPageIndex = savedInstanceState.getInt(KEY_TAB_INDEX)
      setToolbarTitle(currentPageIndex)
    } else {
      setToolbarTitle(0)
    }

  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(KEY_TAB_INDEX, currentPageIndex)
  }

  private inline fun setToolbarTitle(position: Int) {
    supportActionBar!!.setTitle(screens.screens[position].titleRes())
  }

  fun jumpToScreen(screenDetector: (Screen) -> Boolean) {
    screens.screens.forEachIndexed { i, s ->
      if (screenDetector(s)) {
        viewPager.setCurrentItem(i, true)
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.menuLicense) {
      LicensesDialog.Builder(this)
          .setNotices(R.raw.notices)
          .build()
          .show();

      return true
    }

    if (item.itemId == R.id.menuSearch) {
      navigator.showSearch()
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }
}
