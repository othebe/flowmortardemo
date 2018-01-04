package com.othebe.flowmortardemo.ui.navigation

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.othebe.flowmortardemo.common.ActivityContext
import com.othebe.flowmortardemo.common.ActivityScope
import com.othebe.flowmortardemo.ui.MainActivityModule
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module(includes = [ MainActivityModule::class ])
class NavigationModule(private val contentViewId: Int) {
    @Provides
    @ActivityScope
    fun provideContentView(@ActivityContext context: Context): ViewGroup {
        return (context as Activity).findViewById(contentViewId)
    }

    @Provides
    @ActivityScope
    fun provideFlowKeyChanger(screenInflater: Lazy<ScreenInflater>, screenScopeManager: ScreenScopeManager): FlowKeyChanger {
        return FlowKeyChanger(screenInflater, screenScopeManager)
    }

    @Provides
    @ActivityScope
    fun provideScreenInflater(viewGroup: Lazy<ViewGroup>): ScreenInflater {
        return ScreenInflater(viewGroup)
    }

    @Provides
    @ActivityScope
    fun provideScreenScopeManager(@ActivityContext context: Context): ScreenScopeManager {
        return ScreenScopeManager(context)
    }
}