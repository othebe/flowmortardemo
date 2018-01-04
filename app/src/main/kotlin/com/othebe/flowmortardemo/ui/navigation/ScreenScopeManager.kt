package com.othebe.flowmortardemo.ui.navigation

import android.content.Context
import com.othebe.flowmortardemo.common.ActivityContext
import com.othebe.flowmortardemo.ui.screens.ScreenKey
import mortar.MortarScope
import javax.inject.Inject

class ScreenScopeManager @Inject constructor(@ActivityContext private val context: Context) {
    fun destroyScope(screenKey: ScreenKey) {
        MortarScope.findChild(context, screenKey.scopeName).destroy()
    }
}