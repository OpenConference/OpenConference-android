package com.openconfernce

import android.content.Context
import com.openconference.dagger.BackendModule
import com.openconference.model.backend.schedule.BackendScheduleAdapter
import com.openconfernce.mock.MockSchedulerAdapterStub

/**
 * This just mocks some data
 *
 * @author Hannes Dorfmann
 */
class MockBackendModule(c: Context) : BackendModule(c) {

  override fun provideBackendAdapter(): BackendScheduleAdapter = MockSchedulerAdapterStub()
}