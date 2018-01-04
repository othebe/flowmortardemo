package com.othebe.flowmortardemo.ui.navigation

import android.view.ViewGroup
import com.othebe.flowmortardemo.ui.screens.ScreenType
import com.othebe.flowmortardemo.ui.screens.ScreenType.*
import com.othebe.flowmortardemo.ui.screens.feed.FeedScreen
import com.othebe.flowmortardemo.ui.screens.info.InfoScreen
import com.othebe.flowmortardemo.ui.screens.timer.TimerScreen
import dagger.Lazy
import javax.inject.Inject

class ScreenInflater @Inject constructor(private val contentView: Lazy<ViewGroup>) {

    fun inflateScreen(screenType: ScreenType) {
        contentView.get().removeAllViews()

        when (screenType) {
            TIMER -> TimerScreen.inflate(contentView.get())
            FEED -> FeedScreen.inflate(contentView.get())
            INFO -> InfoScreen.inflate(contentView.get())
        }
    }
}