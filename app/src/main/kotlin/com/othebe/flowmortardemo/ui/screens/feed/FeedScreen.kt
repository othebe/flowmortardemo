package com.othebe.flowmortardemo.ui.screens.feed

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.othebe.flowmortardemo.R
import com.othebe.flowmortardemo.common.ActivityContext
import com.othebe.flowmortardemo.common.SERVICE_COMPONENT
import com.othebe.flowmortardemo.common.SERVICE_PRESENTER
import com.othebe.flowmortardemo.common.ScreenScope
import com.othebe.flowmortardemo.ui.MainActivitySubcomponent
import com.othebe.flowmortardemo.ui.screens.ScreenKey
import com.othebe.flowmortardemo.ui.screens.ScreenType
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import mortar.MortarScope
import javax.inject.Inject

class FeedScreen private constructor(@ActivityContext private val context: Context) {
    companion object {
        val NAME = FeedScreen::class.java.name

        fun inflate(contentView: ViewGroup) {
            FeedScreen(contentView.context).inflate(contentView)
        }
    }

    @Inject
    lateinit var presenter: FeedScreenPresenter

    private val scope: MortarScope by lazy {
        MortarScope.findChild(context, NAME) ?: getMortarScope()
    }

    private val component: FeedScreenSubcomponent by lazy {
        (context.getSystemService(SERVICE_COMPONENT) as MainActivitySubcomponent)
                .plus(FeedScreenModule())
    }

    private fun getMortarScope(): MortarScope {
        component.inject(this)
        return MortarScope.buildChild(context)
                .withService(SERVICE_PRESENTER, presenter)
                .build(NAME)
    }

    private fun inflate(contentView: ViewGroup) {
        LayoutInflater.from(scope.createContext(context)).inflate(
                R.layout.screen_feed, contentView)
    }
}

object FeedScreenKey : ScreenKey(ScreenType.FEED, FeedScreen.NAME)

@Module
class FeedScreenModule {
    @Provides
    @ScreenScope
    fun provideFeedScreenPresenter(): FeedScreenPresenter {
        return FeedScreenPresenter()
    }
}

@Subcomponent(modules = [ FeedScreenModule::class ])
@ScreenScope
interface FeedScreenSubcomponent {
    fun inject(FeedScreen: FeedScreen)
}