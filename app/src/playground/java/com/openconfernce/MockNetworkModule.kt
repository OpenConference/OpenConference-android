package com.openconfernce

import android.content.Context
import com.openconference.dagger.NetworkModule
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import com.openconfernce.mock.MockSchedulerAdapterStub

/**
 * This just mocks some data
 *
 * @author Hannes Dorfmann
 */
class MockNetworkModule(c: Context) : NetworkModule(c) {

  override fun provideBackendAdapter(): BackendScheduleAdapter = MockSchedulerAdapterStub()
}