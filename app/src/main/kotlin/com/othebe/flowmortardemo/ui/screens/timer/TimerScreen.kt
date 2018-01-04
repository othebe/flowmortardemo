package com.othebe.flowmortardemo.ui.screens.timer

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

class TimerScreen private constructor(@ActivityContext private val context: Context) {
    companion object {
        val NAME = TimerScreen::class.java.name

        fun inflate(contentView: ViewGroup) {
            TimerScreen(contentView.context).inflate(contentView)
        }
    }

    @Inject
    lateinit var presenter: TimerScreenPresenter

    private val scope: MortarScope by lazy {
        MortarScope.findChild(context, NAME) ?: getMortarScope()
    }

    private val component: TimerScreenSubcomponent by lazy {
        (context.getSystemService(SERVICE_COMPONENT) as MainActivitySubcomponent)
                .plus(TimerScreenModule())
    }

    private fun getMortarScope(): MortarScope {
        component.inject(this)
        return MortarScope.buildChild(context)
                .withService(SERVICE_PRESENTER, presenter)
                .build(NAME)
    }

    private fun inflate(contentView: ViewGroup) {
        LayoutInflater.from(scope.createContext(context)).inflate(
                R.layout.screen_timer, contentView)
    }
}

object TimerScreenKey : ScreenKey(ScreenType.TIMER, TimerScreen.NAME)

@Module
class TimerScreenModule {
    @Provides
    @ScreenScope
    fun provideTimerScreenPresenter(): TimerScreenPresenter {
        return TimerScreenPresenter()
    }
}

@Subcomponent(modules = [ TimerScreenModule::class ])
@ScreenScope
interface TimerScreenSubcomponent {
    fun inject(timerScreen: TimerScreen)
}
