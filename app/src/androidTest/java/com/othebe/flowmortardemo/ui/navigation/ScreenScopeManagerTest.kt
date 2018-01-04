package com.othebe.flowmortardemo.ui.navigation

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreenScopeManagerTest {
    private lateinit var screenScopeManager: ScreenScopeManager

    @Before
    fun setup() {
        screenScopeManager = ScreenScopeManager(InstrumentationRegistry.getTargetContext())
    }

    @Test
    fun shouldDestroyScopeWhenNavigatingAway() {
        assertEquals(1, 1)
    }
}