package com.othebe.flowmortardemo.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.othebe.flowmortardemo.ApplicationComponent
import com.othebe.flowmortardemo.R
import com.othebe.flowmortardemo.common.ActivityContext
import com.othebe.flowmortardemo.common.ActivityScope
import com.othebe.flowmortardemo.common.SERVICE_COMPONENT
import com.othebe.flowmortardemo.ui.navigation.FlowKeyChanger
import com.othebe.flowmortardemo.ui.navigation.NavigationModule
import com.othebe.flowmortardemo.ui.screens.feed.FeedScreenKey
import com.othebe.flowmortardemo.ui.screens.feed.FeedScreenModule
import com.othebe.flowmortardemo.ui.screens.feed.FeedScreenSubcomponent
import com.othebe.flowmortardemo.ui.screens.info.InfoScreenKey
import com.othebe.flowmortardemo.ui.screens.info.InfoScreenModule
import com.othebe.flowmortardemo.ui.screens.info.InfoScreenSubcomponent
import com.othebe.flowmortardemo.ui.screens.timer.TimerScreenKey
import com.othebe.flowmortardemo.ui.screens.timer.TimerScreenModule
import com.othebe.flowmortardemo.ui.screens.timer.TimerScreenSubcomponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import flow.Flow
import flow.KeyDispatcher
import kotlinx.android.synthetic.main.activity_main.*
import mortar.MortarScope
import mortar.bundler.BundleServiceRunner
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    companion object {
        val NAME = MainActivity::class.java.name
    }

    private lateinit var component: MainActivitySubcomponent

    @Inject
    lateinit var flowKeyChanger: FlowKeyChanger

    private val scope: MortarScope by lazy {
        MortarScope.findChild(applicationContext, NAME) ?:
                MortarScope.buildChild(applicationContext)
                        .withService(BundleServiceRunner.SERVICE_NAME, BundleServiceRunner())
                        .withService(SERVICE_COMPONENT, component)
                        .build(NAME)
    }

    private val flow: Flow by lazy { Flow.get(this) }

    override fun onBackPressed() {
        if (!flow.goBack()) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState)
        bottom_nav.setOnNavigationItemSelectedListener(bottomNavListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState)
    }

    override fun attachBaseContext(newBase: Context) {
        val applicationComponent = newBase.applicationContext.getSystemService(SERVICE_COMPONENT) as ApplicationComponent
        component = applicationComponent.plus(MainActivityModule(this), NavigationModule(R.id.content))
        component.inject(this)

        val flowContext = Flow
                .configure(newBase, this)
                .defaultKey(TimerScreenKey)
                .dispatcher(KeyDispatcher.configure(this, flowKeyChanger).build())
                .install()

        super.attachBaseContext(flowContext)
    }

    override fun getSystemService(name: String): Any {
        return if (scope.hasService(name))
            scope.getService(name)
        else
            super.getSystemService(name)
    }

    private val bottomNavListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.bottom_nav_timer -> flow.set(TimerScreenKey)
                R.id.bottom_nav_feed -> flow.set(FeedScreenKey)
                R.id.bottom_nav_info -> flow.set(InfoScreenKey)
            }
            return true
        }
    }
}

@Module
class MainActivityModule(@ActivityContext private val context: Context) {
    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return context
    }
}

@Subcomponent(modules = [ MainActivityModule::class, NavigationModule::class ])
@ActivityScope
interface MainActivitySubcomponent {
    fun inject(mainActivity: MainActivity)

    fun plus(timerScreenModule: TimerScreenModule): TimerScreenSubcomponent
    fun plus(feedScreenModule: FeedScreenModule): FeedScreenSubcomponent
    fun plus(infoScreenModule: InfoScreenModule): InfoScreenSubcomponent
}