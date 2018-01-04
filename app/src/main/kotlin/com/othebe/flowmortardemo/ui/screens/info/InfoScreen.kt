package com.othebe.flowmortardemo.ui.screens.info

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

class InfoScreen private constructor(@ActivityContext private val context: Context) {
    companion object {
        val NAME = InfoScreen::class.java.name

        fun inflate(contentView: ViewGroup) {
            InfoScreen(contentView.context).inflate(contentView)
        }
    }

    @Inject
    lateinit var presenter: InfoScreenPresenter

    private val scope: MortarScope by lazy {
        MortarScope.findChild(context, NAME) ?: getMortarScope()
    }

    private val component: InfoScreenSubcomponent by lazy {
        (context.getSystemService(SERVICE_COMPONENT) as MainActivitySubcomponent)
                .plus(InfoScreenModule())
    }

    private fun getMortarScope(): MortarScope {
        component.inject(this)
        return MortarScope.buildChild(context)
                .withService(SERVICE_PRESENTER, presenter)
                .build(NAME)
    }

    private fun inflate(contentView: ViewGroup) {
        LayoutInflater.from(scope.createContext(context)).inflate(
                R.layout.screen_info, contentView)
    }
}

object InfoScreenKey : ScreenKey(ScreenType.INFO, InfoScreen.NAME)

@Module
class InfoScreenModule {
    @Provides
    @ScreenScope
    fun provideInfoScreenPresenter(): InfoScreenPresenter {
        return InfoScreenPresenter()
    }
}

@Subcomponent(modules = [ InfoScreenModule::class ])
@ScreenScope
interface InfoScreenSubcomponent {
    fun inject(infoScreen: InfoScreen)
}
