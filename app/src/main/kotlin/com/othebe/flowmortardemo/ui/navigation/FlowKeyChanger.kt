package com.othebe.flowmortardemo.ui.navigation

import android.content.Context
import com.othebe.flowmortardemo.ui.screens.ScreenKey
import dagger.Lazy
import flow.Direction
import flow.KeyChanger
import flow.State
import flow.TraversalCallback
import javax.inject.Inject

class FlowKeyChanger @Inject constructor(private val screenInflater: Lazy<ScreenInflater>,
                                         private val screenScopeManager: ScreenScopeManager) : KeyChanger {
    override fun changeKey(outgoingState: State?, incomingState: State, direction: Direction, incomingContexts: MutableMap<Any, Context>, callback: TraversalCallback) {
        if (outgoingState != null && outgoingState != incomingState) screenScopeManager.destroyScope(outgoingState.getKey<ScreenKey>())
        screenInflater.get().inflateScreen(incomingState.getKey<ScreenKey>().screenType)
        callback.onTraversalCompleted()
    }
}