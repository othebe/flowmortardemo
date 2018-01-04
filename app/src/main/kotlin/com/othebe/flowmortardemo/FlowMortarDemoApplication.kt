package com.othebe.flowmortardemo

import android.app.Application
import com.othebe.flowmortardemo.common.SERVICE_COMPONENT
import com.othebe.flowmortardemo.ui.MainActivityModule
import com.othebe.flowmortardemo.ui.MainActivitySubcomponent
import com.othebe.flowmortardemo.ui.navigation.NavigationModule
import dagger.Component
import mortar.MortarScope
import javax.inject.Singleton

class FlowMortarDemoApplication : Application() {
    companion object {
        val NAME = "MessagingForYammerApp"
    }

    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .build()
    }

    private val scope: MortarScope by lazy {
        MortarScope.buildRootScope()
                .withService(SERVICE_COMPONENT, component)
                .build(NAME)
    }

    override fun getSystemService(name: String?): Any {
        component.inject(this)

        return if (scope.hasService(name)) scope.getService(name) else super.getSystemService(name)
    }
}

@Component
@Singleton
interface ApplicationComponent {
    fun inject(flowMortarDemoApplication: FlowMortarDemoApplication)

    fun plus(mainActivityModule: MainActivityModule, navigationModule: NavigationModule): MainActivitySubcomponent
}
